package com.dropsense.DropsenseProject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class EmailService {

    @Value("${gmail.client-id:}")
    private String clientId;

    @Value("${gmail.client-secret:}")
    private String clientSecret;

    @Value("${gmail.refresh-token:}")
    private String refreshToken;

    @Value("${gmail.from-email:}")
    private String fromEmail;

    @Value("${dropsense.app-url:http://localhost:8080}")
    private String appUrl;

    private final RestClient restClient = RestClient.create();

    // 1. Gửi email khôi phục mật khẩu
    public void sendPasswordResetEmail(String toEmail, String token) {

        // Kiểm tra cấu hình Gmail API
        if (clientId.isBlank() || clientSecret.isBlank()
                || refreshToken.isBlank() || fromEmail.isBlank()) {
            throw new IllegalStateException("Chưa cấu hình đầy đủ Gmail API.");
        }

        // Ngăn ký tự xuống dòng trong địa chỉ email
        if (toEmail == null || toEmail.contains("\r") || toEmail.contains("\n")) {
            throw new IllegalArgumentException("Địa chỉ email không hợp lệ.");
        }

        // Tạo liên kết đặt lại mật khẩu
        String resetLink = appUrl.replaceAll("/+$", "")
                + "/reset-password?token="
                + URLEncoder.encode(token, StandardCharsets.UTF_8);

        // Nội dung email
        String html = """
                <h2>Khôi phục mật khẩu DropSense</h2>
                <p>Bạn đã yêu cầu đặt lại mật khẩu.</p>
                <p>Nhấn vào liên kết bên dưới:</p>
                <p><a href="%s">Đặt lại mật khẩu</a></p>
                <p>Nếu không yêu cầu, hãy bỏ qua email này.</p>
                """.formatted(resetLink);

        // 2. Dùng Refresh Token để lấy Access Token
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);
        form.add("grant_type", "refresh_token");

        Map<?, ?> tokenResponse = restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(Map.class);

        if (tokenResponse == null || !(tokenResponse.get("access_token") instanceof String accessToken)) {
            throw new IllegalStateException("Không lấy được Access Token từ Google.");
        }

        // 3. Tạo nội dung email theo định dạng MIME
        String encodedHtml = Base64.getMimeEncoder(
                76, "\r\n".getBytes(StandardCharsets.US_ASCII)
        ).encodeToString(html.getBytes(StandardCharsets.UTF_8));

        String mimeMessage = "From: " + fromEmail + "\r\n"
                + "To: " + toEmail + "\r\n"
                + "Subject: DropSense - Khoi phuc mat khau\r\n"
                + "MIME-Version: 1.0\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Transfer-Encoding: base64\r\n"
                + "\r\n"
                + encodedHtml;

        // 4. Chuyển email sang Base64 URL-safe
        String raw = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(mimeMessage.getBytes(StandardCharsets.UTF_8));

        // 5. Gửi email bằng Gmail API qua HTTPS
        restClient.post()
                .uri("https://gmail.googleapis.com/gmail/v1/users/me/messages/send")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("raw", raw))
                .retrieve()
                .toBodilessEntity();
    }
}
