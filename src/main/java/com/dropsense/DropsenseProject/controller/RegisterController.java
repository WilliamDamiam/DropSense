package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.entity.Role;
import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
public class RegisterController
{
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder)
    {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage()
    {
        return "register";
    }

    // 2. Xử lý đăng ký tài khoản
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model)
    {
        username = username.trim().toLowerCase(Locale.ROOT);
        email = email.trim().toLowerCase(Locale.ROOT);

        // Kiểm tra tên đăng nhập
        if (!username.matches("[a-z0-9_]{5,30}"))
        {
            return showError("Tên đăng nhập phải có 5-30 ký tự, chỉ gồm chữ, số hoặc dấu gạch dưới.", username, email, model);
        }

        // Kiểm tra email
        if (email.length() > 254 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
        {
            return showError("Email không hợp lệ.", username, email, model);
        }

        // Kiểm tra mật khẩu
        if (password.length() < 10 ||
                password.getBytes(StandardCharsets.UTF_8).length > 72 ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[^\\p{L}\\p{N}\\s].*"))
        {
            return showError("Mật khẩu phải có ít nhất 10 ký tự, gồm số và ký tự đặc biệt, tối đa 72 byte.", username, email, model);
        }

        // Kiểm tra nhập lại mật khẩu
        if (!password.equals(confirmPassword))
        {
            return showError("Mật khẩu nhập lại không khớp.", username, email, model);
        }

        // Kiểm tra tài khoản đã tồn tại
        if (userAccountRepository.existsByUsernameIgnoreCase(username) ||
                userAccountRepository.existsByEmailIgnoreCase(email))
        {
            return showError("Tên đăng nhập hoặc email đã được sử dụng.", username, email, model);
        }

        // Tạo tài khoản mới
        UserAccount account = new UserAccount();

        account.setUsername(username);
        account.setEmail(email);

        // Mã hóa mật khẩu bằng BCrypt
        account.setPassword(passwordEncoder.encode(password));

        // Tài khoản đăng ký mới chỉ được quyền VIEWER
        account.setRole(Role.VIEWER);
        account.setEnabled(true);

        try
        {
            userAccountRepository.saveAndFlush(account);
        }
        catch (DataIntegrityViolationException ex)
        {
            return showError("Tên đăng nhập hoặc email đã được sử dụng.", username, email, model);
        }

        // Đăng ký thành công, chuyển về trang đăng nhập
        return "redirect:/login?registered";
    }

    // 3. Hiển thị thông báo lỗi
    private String showError(String message, String username, String email, Model model)
    {
        model.addAttribute("error", message);
        model.addAttribute("username", username);
        model.addAttribute("email", email);

        return "register";
    }
}
