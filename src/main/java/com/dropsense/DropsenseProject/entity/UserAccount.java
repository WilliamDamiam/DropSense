package com.dropsense.DropsenseProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_accounts")
public class UserAccount
{
    // Khóa chính của tài khoản, tự động tăng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Tên đăng nhập, không được trùng
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    // Mật khẩu đã được mã hóa bằng BCrypt
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    // Vai trò của tài khoản: ADMIN hoặc VIEWER
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    // Trạng thái tài khoản: true là được phép đăng nhập
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    // Tên file ảnh đại diện của tài khoản
    @Column(name = "avatar_file_name", length = 100)
    private String avatarFileName;

    // Email của người dùng
    @Column(name = "email", length = 254, unique = true)
    private String email;

    // Constructor không tham số dành cho JPA
    public UserAccount()
    {
    }

    // Getter và Setter của id
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    // Getter và Setter của username
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    // Getter và Setter của password
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    // Getter và Setter của role
    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    // Getter và Setter của enabled
    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getAvatarFileName()
    {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName)
    {
        this.avatarFileName = avatarFileName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

}
