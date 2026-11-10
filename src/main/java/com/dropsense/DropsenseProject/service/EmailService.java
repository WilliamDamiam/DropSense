package com.dropsense.DropsenseProject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String senderEmail;

    @Value("${dropsense.app-url:http://localhost:8080}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    // Gửi email đặt lại mật khẩu
    public void sendPasswordResetEmail(String toEmail, String token)
    {
        // 1. Kiểm tra email gửi
        if (senderEmail == null || senderEmail.isBlank())
        {
            throw new IllegalStateException("Chưa cấu hình email gửi.");
        }

        // 2. Tạo liên kết khôi phục mật khẩu
        String baseUrl = appUrl;

        if (baseUrl.endsWith("/"))
        {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        String resetLink = baseUrl + "/reset-password?token=" + token;

        // 3. Nội dung email dạng văn bản thông thường
        String plainText =
                "Xin chào,\n\n" +
                        "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản DropSense của bạn.\n\n" +
                        "Để tiếp tục, vui lòng truy cập liên kết sau:\n" +
                        resetLink + "\n\n" +
                        "Liên kết chỉ có hiệu lực trong 15 phút và chỉ được sử dụng một lần.\n\n" +
                        "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.\n" +
                        "Mật khẩu hiện tại của bạn sẽ không thay đổi.\n\n" +
                        "Trân trọng,\n" +
                        "Đội ngũ DropSense\n\n" +
                        "Đây là email tự động, vui lòng không trả lời.";

        // 4. Nội dung email dạng HTML
        String htmlText =
                "<!DOCTYPE html>" +
                        "<html lang='vi'>" +
                        "<body style='margin:0;padding:30px 10px;background-color:#f1f5f9;font-family:Arial,sans-serif;color:#1e293b;'>" +

                        "<div style='max-width:600px;margin:auto;background-color:white;border-radius:12px;overflow:hidden;'>" +

                        // Phần đầu email
                        "<div style='background-color:#1e3a8a;padding:30px;color:white;'>" +
                        "<h1 style='margin:0;font-size:28px;color:white;'>DropSense</h1>" +
                        "<p style='margin:10px 0 0;color:#bfdbfe;font-size:14px;'>" +
                        "Hệ thống cảnh báo nguy cơ bỏ học</p>" +
                        "</div>" +

                        // Nội dung chính
                        "<div style='padding:35px 30px;'>" +

                        "<h2 style='font-size:23px;color:#0f172a;margin-top:0;'>" +
                        "Yêu cầu đặt lại mật khẩu" +
                        "</h2>" +

                        "<p style='line-height:1.8;'>Xin chào,</p>" +

                        "<p style='line-height:1.8;'>" +
                        "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu " +
                        "cho tài khoản DropSense của bạn." +
                        "</p>" +

                        "<p style='line-height:1.8;'>" +
                        "Để tiếp tục, vui lòng nhấn vào nút bên dưới " +
                        "và tạo mật khẩu mới." +
                        "</p>" +

                        // Nút đặt lại mật khẩu
                        "<div style='text-align:center;margin:35px 0;'>" +
                        "<a href='" + resetLink + "' " +
                        "style='display:inline-block;background-color:#2563eb;" +
                        "color:white;text-decoration:none;padding:15px 30px;" +
                        "border-radius:8px;font-weight:bold;font-size:16px;'>" +
                        "Đặt lại mật khẩu" +
                        "</a>" +
                        "</div>" +

                        // Thông báo hết hạn
                        "<div style='background-color:#eff6ff;padding:16px;border-radius:8px;'>" +
                        "<p style='margin:0;color:#1e40af;font-size:14px;line-height:1.7;'>" +
                        "Liên kết chỉ có hiệu lực trong <strong>15 phút</strong> " +
                        "và chỉ được sử dụng một lần." +
                        "</p>" +
                        "</div>" +

                        // Liên kết dự phòng
                        "<p style='font-size:13px;color:#64748b;line-height:1.7;margin-top:25px;'>" +
                        "Nếu nút phía trên không hoạt động, " +
                        "hãy sao chép liên kết sau vào trình duyệt:" +
                        "</p>" +

                        "<p style='font-size:12px;overflow-wrap:anywhere;word-break:break-word;'>" +
                        "<a href='" + resetLink + "' style='color:#2563eb;'>" +
                        resetLink +
                        "</a></p>" +

                        // Lời nhắc bảo mật
                        "<p style='line-height:1.8;margin-top:25px;'>" +
                        "Nếu bạn không thực hiện yêu cầu này, " +
                        "vui lòng bỏ qua email. " +
                        "Mật khẩu hiện tại của bạn sẽ không thay đổi." +
                        "</p>" +

                        "<p style='line-height:1.8;margin-top:30px;'>" +
                        "Trân trọng,<br>" +
                        "<strong>Đội ngũ DropSense</strong>" +
                        "</p>" +

                        "</div>" +

                        // Chân trang
                        "<div style='background-color:#f8fafc;padding:20px;text-align:center;" +
                        "border-top:1px solid #e2e8f0;'>" +

                        "<p style='color:#64748b;font-size:12px;margin:0 0 8px;'>" +
                        "© 2026 DropSense - Nhóm 2A" +
                        "</p>" +

                        "<p style='color:#94a3b8;font-size:12px;margin:0;'>" +
                        "Đây là email tự động, vui lòng không trả lời." +
                        "</p>" +

                        "</div>" +

                        "</div>" +
                        "</body></html>";

        // 5. Tạo và gửi email HTML
        try
        {
            MimeMessage message = mailSender.createMimeMessage();

            // UTF-8 giúp hiển thị tiếng Việt có dấu
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("DropSense | Yêu cầu đặt lại mật khẩu");

            // Gửi cả bản văn bản và bản HTML
            helper.setText(plainText, htmlText);

            mailSender.send(message);
        }
        catch (MessagingException ex)
        {
            throw new MailPreparationException("Không thể tạo email khôi phục mật khẩu.", ex);
        }
    }
}
