package com.dropsense.DropsenseProject.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController
{
    // 1. Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request)
    {
        // Lấy CSRF token từ Spring Security
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Tạo token trước khi Thymeleaf xử lý form
        if (csrfToken != null)
        {
            csrfToken.getToken();
        }

        return "login";
    }

    // 2. Hiển thị trang chủ
    @GetMapping("/")
    public String showHomePage()
    {
        return "index";
    }
}
