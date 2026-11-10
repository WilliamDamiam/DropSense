package com.dropsense.DropsenseProject.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tài khoản yêu cầu đặt lại mật khẩu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount userAccount;

    // Mã khôi phục đã được băm
    @Column(name = "token_hash", nullable = false, unique = true, length = 64)
    private String tokenHash;

    // Thời điểm hết hạn của mã
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    // Kiểm tra mã đã được sử dụng chưa
    @Column(nullable = false)
    private boolean used = false;

    public PasswordResetToken()
    {

    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public String getTokenHash()
    {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash)
    {
        this.tokenHash = tokenHash;
    }

    public Instant getExpiresAt()
    {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt)
    {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed(boolean used)
    {
        this.used = used;
    }
}
