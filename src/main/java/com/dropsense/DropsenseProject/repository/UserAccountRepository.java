package com.dropsense.DropsenseProject.repository;

import com.dropsense.DropsenseProject.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>
{
    // Tìm tài khoản theo username để đăng nhập
    Optional<UserAccount> findByUsername(String username);

    // Kiểm tra username đã tồn tại
    boolean existsByUsername(String username);

    // Kiểm tra username đã tồn tại, không phân biệt hoa thường
    boolean existsByUsernameIgnoreCase(String username);

    // Kiểm tra email đã tồn tại, không phân biệt hoa thường
    boolean existsByEmailIgnoreCase(String email);

    // Tìm tài khoản theo email
    Optional<UserAccount> findByEmailIgnoreCase(String email);
}
