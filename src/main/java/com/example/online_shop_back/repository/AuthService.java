package com.example.online_shop_back.repository;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RegisterDTO;

public interface AuthService {
    public ApiResult register(RegisterDTO registerDTO);
}
