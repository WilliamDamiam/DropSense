package com.dropsense.DropsenseProject.config;

import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

@ControllerAdvice
public class CurrentUserAdvice
{
    private final UserAccountRepository userAccountRepository;

    @Value("${dropsense.avatar-dir:uploads/avatars}")
    private String avatarDirectory;

    public CurrentUserAdvice(UserAccountRepository userAccountRepository)
    {
        this.userAccountRepository = userAccountRepository;
    }

    @ModelAttribute
    public void addCurrentUser(Model model, Authentication authentication)
    {
        // 1. Kiểm tra người dùng đã đăng nhập chưa
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken)
        {
            return;
        }

        String username = authentication.getName();

        if (username == null || username.isBlank())
        {
            return;
        }

        // 2. Lấy tên đăng nhập
        model.addAttribute("currentUsername", username);

        // 3. Lấy chữ cái đầu để làm avatar mặc định
        String avatarInitial = username.substring(0, 1).toUpperCase(Locale.ROOT);

        model.addAttribute("avatarInitial", avatarInitial);

        // 4. Xác định vai trò
        String currentRole = "Người xem";

        for (GrantedAuthority authority : authentication.getAuthorities())
        {
            if (authority.getAuthority().equals("ROLE_ADMIN"))
            {
                currentRole = "Quản trị viên";
                break;
            }
        }

        model.addAttribute("currentRole", currentRole);

        // 5. Kiểm tra tài khoản có ảnh đại diện hay chưa
        boolean hasAvatar = false;

        Optional<UserAccount> optionalAccount = userAccountRepository.findByUsername(username);

        if (optionalAccount.isPresent())
        {
            UserAccount account = optionalAccount.get();

            String avatarFileName = account.getAvatarFileName();

            if (avatarFileName != null && !avatarFileName.isBlank())
            {
                Path folder = Path.of(avatarDirectory).toAbsolutePath().normalize();
                Path imagePath = folder.resolve(avatarFileName).normalize();

                // Chỉ hiển thị khi ảnh thực sự tồn tại trong thư mục upload
                if (imagePath.startsWith(folder) && Files.isRegularFile(imagePath))
                {
                    hasAvatar = true;

                    // Giúp trình duyệt nhận biết khi người dùng đổi ảnh
                    model.addAttribute("avatarVersion", avatarFileName);
                }
            }
        }

        model.addAttribute("hasAvatar", hasAvatar);
    }
}
