package com.dropsense.DropsenseProject.controller;

import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController
{
    private final UserAccountRepository userAccountRepository;

    // Thư mục lưu ảnh, nằm ngoài resources/static
    @Value("${dropsense.avatar-dir:uploads/avatars}")
    private String avatarDirectory;

    public ProfileController(UserAccountRepository userAccountRepository)
    {
        this.userAccountRepository = userAccountRepository;
    }

    // 1. Hiển thị trang hồ sơ cá nhân
    @GetMapping
    public String showProfile(Authentication authentication, Model model)
    {
        UserAccount account = getCurrentAccount(authentication);

        // Kiểm tra tài khoản đã có ảnh đại diện chưa
        boolean hasAvatar = false;

        if (account.getAvatarFileName() != null)
        {
            Path imagePath = getAvatarFolder().resolve(account.getAvatarFileName()).normalize();

            hasAvatar = imagePath.startsWith(getAvatarFolder()) && Files.isRegularFile(imagePath);
        }

        model.addAttribute("hasAvatar", hasAvatar);

        return "profile";
    }

    // 2. Cập nhật ảnh đại diện
    @PostMapping("/avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatar,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes)
    {
        if (avatar.isEmpty())
        {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ảnh đại diện.");
            return "redirect:/profile";
        }

        // Giới hạn ảnh tối đa 2 MB
        if (avatar.getSize() > 2 * 1024 * 1024)
        {
            redirectAttributes.addFlashAttribute("error", "Ảnh không được vượt quá 2 MB.");
            return "redirect:/profile";
        }

        try
        {
            byte[] imageBytes = avatar.getBytes();

            // Kiểm tra định dạng dựa trên dữ liệu ảnh
            String extension = detectImageExtension(imageBytes);

            if (extension == null)
            {
                redirectAttributes.addFlashAttribute("error", "Chỉ chấp nhận ảnh JPG hoặc PNG.");
                return "redirect:/profile";
            }

            // Kiểm tra ảnh có đọc được và kích thước hợp lệ
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

            if (image == null || image.getWidth() > 4096 || image.getHeight() > 4096)
            {
                redirectAttributes.addFlashAttribute("error", "Ảnh không hợp lệ hoặc có kích thước quá lớn.");
                return "redirect:/profile";
            }

            UserAccount account = getCurrentAccount(authentication);

            // Tạo thư mục lưu ảnh
            Path folder = getAvatarFolder();
            Files.createDirectories(folder);

            // Đổi tên file bằng UUID để tránh trùng tên
            String fileName = UUID.randomUUID() + "." + extension;
            Path imagePath = folder.resolve(fileName);

            Files.write(imagePath, imageBytes, StandardOpenOption.CREATE_NEW);

            // Lưu tên ảnh vào tài khoản MySQL
            String oldFileName = account.getAvatarFileName();

            account.setAvatarFileName(fileName);

            try
            {
                userAccountRepository.save(account);
            }
            catch (RuntimeException ex)
            {
                // Nếu lưu MySQL thất bại thì xóa ảnh vừa tải lên
                Files.deleteIfExists(imagePath);
                throw ex;
            }

            // Xóa ảnh cũ sau khi đã lưu ảnh mới thành công
            if (oldFileName != null && oldFileName.matches("[0-9a-fA-F-]{36}\\.(jpg|png)"))
            {
                Path oldImagePath = folder.resolve(oldFileName);

                try
                {
                    Files.deleteIfExists(oldImagePath);
                }
                catch (IOException ex)
                {
                    // Không ảnh hưởng đến ảnh mới đã lưu thành công
                }
            }

            redirectAttributes.addFlashAttribute("success", "Cập nhật ảnh đại diện thành công!");
        }
        catch (IOException ex)
        {
            redirectAttributes.addFlashAttribute("error", "Không thể lưu ảnh đại diện.");
        }

        return "redirect:/profile";
    }

    // 3. Đọc ảnh đại diện của tài khoản đang đăng nhập
    @GetMapping("/avatar")
    public ResponseEntity<byte[]> showAvatar(Authentication authentication) throws IOException
    {
        UserAccount account = getCurrentAccount(authentication);

        String fileName = account.getAvatarFileName();

        if (fileName == null)
        {
            return ResponseEntity.notFound().build();
        }

        Path folder = getAvatarFolder();
        Path imagePath = folder.resolve(fileName).normalize();

        // Không cho phép truy cập file nằm ngoài thư mục avatar
        if (!imagePath.startsWith(folder) || !Files.isRegularFile(imagePath))
        {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = fileName.endsWith(".png")
                ? MediaType.IMAGE_PNG
                : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header("X-Content-Type-Options", "nosniff")
                .body(Files.readAllBytes(imagePath));
    }

    // 4. Lấy tài khoản hiện tại từ MySQL
    private UserAccount getCurrentAccount(Authentication authentication)
    {
        if (authentication == null)
        {
            throw new IllegalStateException("Người dùng chưa đăng nhập.");
        }

        return userAccountRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản."));
    }

    // 5. Lấy đường dẫn thư mục lưu ảnh
    private Path getAvatarFolder()
    {
        return Path.of(avatarDirectory).toAbsolutePath().normalize();
    }

    // 6. Kiểm tra chữ ký file PNG hoặc JPG
    private String detectImageExtension(byte[] bytes)
    {
        if (bytes.length >= 8 &&
                (bytes[0] & 0xFF) == 0x89 &&
                bytes[1] == 0x50 &&
                bytes[2] == 0x4E &&
                bytes[3] == 0x47 &&
                bytes[4] == 0x0D &&
                bytes[5] == 0x0A &&
                bytes[6] == 0x1A &&
                bytes[7] == 0x0A)
        {
            return "png";
        }

        if (bytes.length >= 3 &&
                (bytes[0] & 0xFF) == 0xFF &&
                (bytes[1] & 0xFF) == 0xD8 &&
                (bytes[2] & 0xFF) == 0xFF)
        {
            return "jpg";
        }

        return null;
    }
}
