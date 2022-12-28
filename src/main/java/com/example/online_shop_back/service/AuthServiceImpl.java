package com.example.online_shop_back.service;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RegisterDTO;
import com.example.online_shop_back.repository.AuthService;
import com.example.online_shop_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        return userRepository
                .findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }
}
