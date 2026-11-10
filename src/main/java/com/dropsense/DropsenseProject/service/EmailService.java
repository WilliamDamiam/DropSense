package com.dropsense.DropsenseProject.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmailService
{
    @Value("${resend.api-key:}")
    private String resendApiKey;

    @Value("${resend.from-email:onboarding@resend.dev}")
    private String fromEmail;

    @Value("${dropsense.app-url:http://localhost:8080}")
    private String appUrl;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://api.resend.com")
            .build();

    // Giữ nguyên tên hàm và tham số để ForgotPasswordController không cần sửa.
    public void sendPasswordResetEmail(String toEmail, String token)
    {
        if (resendApiKey == null || resendApiKey.isBlank())
        {
            throw new IllegalStateException("Chua cau hinh RESEND_API_KEY");
        }

        // Mã hóa token để dùng an toàn trong URL.
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String baseUrl = appUrl.replaceAll("/+$", "");
        String resetLink = baseUrl + "/reset-password?token=" + encodedToken;

        // Email HTML đơn giản, có thể thay bằng HTML template cũ nếu muốn.
        String html = """
                <!DOCTYPE html>
                <html lang="vi">
                <body style="margin:0;padding:32px 12px;background:#f4f7fb;font-family:Arial,sans-serif;color:#17243c">
                  <div style="max-width:540px;margin:auto;background:#fff;border-radius:16px;padding:32px;border:1px solid #e4eaf4">
                    <h2 style="color:#175cd3;margin-top:0">DropSense</h2>
                    <h3>Khôi phục mật khẩu</h3>
                    <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản DropSense của bạn.</p>
                    <p>Nhấn nút bên dưới để tạo mật khẩu mới:</p>
                    <p style="margin:28px 0">
                      <a href="%s" style="display:inline-block;background:#175cd3;color:white;text-decoration:none;padding:14px 24px;border-radius:10px;font-weight:bold">Đặt lại mật khẩu</a>
                    </p>
                    <p>Liên kết có hiệu lực trong <b>15 phút</b> và chỉ sử dụng được một lần.</p>
                    <p>Nếu không yêu cầu đổi mật khẩu, bạn có thể bỏ qua email này.</p>
                    <hr style="border:none;border-top:1px solid #e4eaf4;margin:24px 0">
                    <p style="font-size:12px;color:#77859a">DropSense · Hệ thống hỗ trợ cảnh báo nguy cơ bỏ học</p>
                  </div>
                </body>
                </html>
                """.formatted(resetLink);

        // Gửi bằng HTTPS API, không sử dụng cổng SMTP bị Railway chặn.
        Map<String, Object> request = Map.of(
                "from", fromEmail,
                "to", List.of(toEmail),
                "subject", "DropSense - Khoi phuc mat khau",
                "html", html
        );

        restClient.post()
                .uri("/emails")
                .headers(headers -> headers.setBearerAuth(resendApiKey))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
