package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;
import com.dropsense.DropsenseProject.service.PasswordResetService;
import com.dropsense.DropsenseProject.service.EmailService;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;
import java.util.Optional;

@Controller
public class ForgotPasswordController
{
    private final UserAccountRepository userAccountRepository;
    private final PasswordResetService passwordResetService;
    private final EmailService emailService;

    public ForgotPasswordController(UserAccountRepository userAccountRepository,
                                    PasswordResetService passwordResetService,
                                    EmailService emailService)
    {
        this.userAccountRepository = userAccountRepository;
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
    }

    // 1. Hiển thị trang quên mật khẩu
    @GetMapping("/forgot-password")
    public String showForgotPassword()
    {
        return "forgot-password";
    }

    // 2. Xử lý yêu cầu quên mật khẩu
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email)
    {
        // Chuẩn hóa email người dùng nhập
        email = email.trim().toLowerCase(Locale.ROOT);

        // Tìm tài khoản theo email
        Optional<UserAccount> optionalAccount = userAccountRepository.findByEmailIgnoreCase(email);

        if (optionalAccount.isPresent())
        {
            UserAccount account = optionalAccount.get();

            if (account.isEnabled())
            {
                // Tạo mã khôi phục
                String token = passwordResetService.createResetToken(account);

                try
                {
                    // Gửi liên kết khôi phục qua email
                    emailService.sendPasswordResetEmail(account.getEmail(), token);
                }
                catch (MailException ex)
                {
                    System.err.println("Lỗi gửi email khôi phục:");
                    ex.printStackTrace();
                }
            }
        }

        // Luôn thông báo giống nhau, kể cả email không tồn tại
        return "redirect:/forgot-password?sent";
    }

    // 3. Hiển thị trang đặt lại mật khẩu
    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam(required = false) String token, Model model)
    {
        // Kiểm tra mã khôi phục
        boolean validToken = passwordResetService.isValidToken(token);

        model.addAttribute("validToken", validToken);

        if (!validToken)
        {
            model.addAttribute("error", "Liên kết không hợp lệ hoặc đã hết hạn.");
            return "reset-password";
        }

        model.addAttribute("token", token);

        return "reset-password";
    }

    // 4. Xử lý đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model)
    {
        try
        {
            // Kiểm tra token và cập nhật mật khẩu mới
            passwordResetService.resetPassword(token, newPassword, confirmPassword);

            // Đổi mật khẩu thành công
            return "redirect:/login?resetSuccess";
        }
        catch (IllegalArgumentException ex)
        {
            // Hiển thị lỗi trên trang
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("token", token);
            model.addAttribute("validToken", passwordResetService.isValidToken(token));

            return "reset-password";
        }
    }
}
