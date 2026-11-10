package com.dropsense.DropsenseProject.repository;

import com.dropsense.DropsenseProject.entity.PasswordResetToken;
import com.dropsense.DropsenseProject.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>
{
    // 1. Tìm mã đặt lại mật khẩu theo token đã băm
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    // 2. Xóa các mã đặt lại mật khẩu cũ của một tài khoản
    void deleteByUserAccount(UserAccount userAccount);
}
