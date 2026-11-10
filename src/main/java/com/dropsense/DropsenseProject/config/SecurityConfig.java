
package com.dropsense.DropsenseProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    // 1. Cấu hình bảo mật cho DropSense
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(auth -> auth

                // Cho phép người chưa đăng nhập sử dụng các trang này
                .requestMatchers(
                        "/login",
                        "/register",
                        "/forgot-password",
                        "/reset-password",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico"
                ).permitAll()

                // ADMIN và VIEWER được cập nhật avatar của chính mình
                .requestMatchers(HttpMethod.POST, "/profile/avatar").hasAnyRole("ADMIN", "VIEWER")

                // Người đăng nhập được đăng xuất
                .requestMatchers(HttpMethod.POST, "/logout").authenticated()

                // Chỉ ADMIN được truy cập trang thêm và sửa sinh viên
                .requestMatchers(
                        "/students/new",
                        "/students/edit/**",
                        "/students/delete/**"
                ).hasRole("ADMIN")

                // Chỉ ADMIN được thêm, sửa, xóa và thực hiện đánh giá
                .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                // Các trang còn lại yêu cầu đăng nhập
                .anyRequest().hasAnyRole("ADMIN", "VIEWER")
        );

        // 2. Cấu hình trang đăng nhập riêng
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
        );

        // 3. Cấu hình đăng xuất
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

    // 4. Mã hóa mật khẩu bằng BCrypt
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
