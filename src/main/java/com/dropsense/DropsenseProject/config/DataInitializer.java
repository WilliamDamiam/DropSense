package com.dropsense.DropsenseProject.config;

import com.dropsense.DropsenseProject.entity.Role;
import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner
{
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${DROPSENSE_ADMIN_PASSWORD:}")
    private String adminPassword;

    @Value("${DROPSENSE_VIEWER_PASSWORD:}")
    private String viewerPassword;

    @Value("${DROPSENSE_VIEWER_USERNAME}")
    private String viewerUsername;

    @Value("${DROPSENSE_ADMIN_USERNAME}")
    private String adminUsername;

    public DataInitializer(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder)
    {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args)
    {
        // Tạo tài khoản quản trị mặc định
        createAccount(adminUsername, adminPassword, Role.ADMIN);

        // Tạo tài khoản xem dữ liệu mặc định
        createAccount(viewerUsername, viewerPassword, Role.VIEWER);
    }

    // Chỉ tạo tài khoản nếu chưa tồn tại
    private void createAccount(String username, String password, Role role)
    {
        if (userAccountRepository.existsByUsername(username))
        {
            System.out.println("Tài khoản " + username + " đã tồn tại.");
            return;
        }

        if (password == null || password.isBlank())
        {
            System.out.println("Chưa cấu hình mật khẩu cho " + username);
            return;
        }

        UserAccount account = new UserAccount();

        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(role);
        account.setEnabled(true);

        userAccountRepository.save(account);

        System.out.println("Đã tạo tài khoản: " + username);
    }
}
