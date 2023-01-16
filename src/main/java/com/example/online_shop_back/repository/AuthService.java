package com.example.online_shop_back.repository;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RegisterDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    public ApiResult register(RegisterDTO registerDTO);

    UserDetails loadUserByUsername(String s);

    UserDetails loadUserByUserId(String userIdFromToken);
}
