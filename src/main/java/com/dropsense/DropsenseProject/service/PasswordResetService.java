package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.entity.PasswordResetToken;
import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.PasswordResetTokenRepository;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class PasswordResetService
{
    private final PasswordResetTokenRepository tokenRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordResetService(PasswordResetTokenRepository tokenRepository,
                                UserAccountRepository userAccountRepository,
                                PasswordEncoder passwordEncoder)
    {
        this.tokenRepository = tokenRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Tạo mã khôi phục mật khẩu
    @Transactional
    public String createResetToken(UserAccount account)
    {
        // Kiểm tra tài khoản
        if (account == null || !account.isEnabled())
        {
            throw new IllegalArgumentException("Tài khoản không hợp lệ.");
        }

        // Xóa các mã khôi phục cũ của tài khoản
        tokenRepository.deleteByUserAccount(account);
        tokenRepository.flush();

        // Tạo mã ngẫu nhiên gồm 32 byte
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        // Chuyển mã sang chuỗi để gửi qua email
        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        // Tạo đối tượng lưu vào MySQL
        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setUserAccount(account);
        resetToken.setTokenHash(hashToken(token));

        // Mã hết hạn sau 15 phút
        resetToken.setExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        // Trả về mã gốc để gửi email, không lưu mã gốc vào MySQL
        return token;
    }

    // 2. Kiểm tra mã khôi phục có hợp lệ hay không
    public boolean isValidToken(String token)
    {
        if (token == null || token.isBlank())
        {
            return false;
        }

        String tokenHash = hashToken(token);

        PasswordResetToken resetToken = tokenRepository.findByTokenHash(tokenHash).orElse(null);

        if (resetToken == null)
        {
            return false;
        }

        // Mã đã sử dụng
        if (resetToken.isUsed())
        {
            return false;
        }

        // Mã đã hết hạn
        if (!resetToken.getExpiresAt().isAfter(Instant.now()))
        {
            return false;
        }

        return true;
    }

    // 3. Đặt lại mật khẩu
    @Transactional
    public void resetPassword(String token, String newPassword, String confirmPassword)
    {
        // Kiểm tra mật khẩu nhập lại
        if (newPassword == null || !newPassword.equals(confirmPassword))
        {
            throw new IllegalArgumentException("Mật khẩu nhập lại không khớp.");
        }

        // Kiểm tra độ mạnh mật khẩu
        if (newPassword.length() < 10 ||
                newPassword.getBytes(StandardCharsets.UTF_8).length > 72 ||
                !newPassword.matches(".*[0-9].*") ||
                !newPassword.matches(".*[^\\p{L}\\p{N}\\s].*"))
        {
            throw new IllegalArgumentException("Mật khẩu cần ít nhất 10 ký tự, có số và ký tự đặc biệt, tối đa 72 byte.");
        }

        if (token == null || token.isBlank())
        {
            throw new IllegalArgumentException("Liên kết khôi phục không hợp lệ.");
        }

        // Tìm mã khôi phục
        String tokenHash = hashToken(token);

        PasswordResetToken resetToken = tokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Liên kết khôi phục không hợp lệ."));

        // Kiểm tra mã đã dùng hay hết hạn
        if (resetToken.isUsed() || !resetToken.getExpiresAt().isAfter(Instant.now()))
        {
            throw new IllegalArgumentException("Liên kết khôi phục đã hết hạn hoặc đã được sử dụng.");
        }

        // Lấy tài khoản cần đổi mật khẩu
        UserAccount account = resetToken.getUserAccount();

        if (!account.isEnabled())
        {
            throw new IllegalArgumentException("Tài khoản không khả dụng.");
        }

        // Mã hóa mật khẩu mới
        account.setPassword(passwordEncoder.encode(newPassword));

        // Cập nhật mật khẩu trong MySQL
        userAccountRepository.save(account);

        // Đánh dấu mã đã sử dụng
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    // 4. Băm mã khôi phục bằng SHA-256
    private String hashToken(String token)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hashBytes);
        }
        catch (NoSuchAlgorithmException ex)
        {
            throw new IllegalStateException("Không thể tạo mã bảo mật.", ex);
        }
    }
}
