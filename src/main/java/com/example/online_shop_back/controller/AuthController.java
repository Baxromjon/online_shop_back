package com.example.online_shop_back.controller;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.LoginDTO;
import com.example.online_shop_back.payload.RegisterDTO;
import com.example.online_shop_back.repository.UserRepository;
import com.example.online_shop_back.security.JwtTokenProvider;
import com.example.online_shop_back.repository.AuthService;
import com.example.online_shop_back.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    AuthServiceImpl authServiceImpl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    HttpEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        UserDetails userDetails = authServiceImpl.loadUserByUsername(registerDTO.getPhoneNumber());
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/login")
    public HttpEntity<?> checkLogin(@Valid @RequestBody LoginDTO userDTO) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            userDTO.getPhoneNumber(),
                            userDTO.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String token = jwtTokenProvider.generateAccessToken(user, new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.status(200).body(new ApiResult<>(token, true, "Successfully"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResponseEntity.status(409).body(new ApiResult<>(false, "Bad Credentials"));
    }

}
