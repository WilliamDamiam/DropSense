package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.entity.UserAccount;
import com.dropsense.DropsenseProject.repository.UserAccountRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService
{
    private final UserAccountRepository userAccountRepository;

    // Constructor để Spring tự truyền Repository
    public AccountDetailsService(UserAccountRepository userAccountRepository)
    {
        this.userAccountRepository = userAccountRepository;
    }

    // Tìm tài khoản trong MySQL khi người dùng đăng nhập
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // Tìm tài khoản theo username
        Optional<UserAccount> result = userAccountRepository.findByUsername(username);

        // Nếu không tìm thấy thì báo lỗi
        if (result.isEmpty())
        {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản!");
        }

        // Lấy thông tin tài khoản
        UserAccount account = result.get();

        // Chuyển tài khoản MySQL thành UserDetails của Spring Security
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().name())
                .disabled(!account.isEnabled())
                .build();
    }
}
