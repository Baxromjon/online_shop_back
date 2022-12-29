package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PasswordDTO;
import com.example.online_shop_back.payload.UserDTO;
import com.example.online_shop_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/edit_password/{id}")
    public HttpEntity<?> editPassword(@PathVariable UUID id, @Valid @RequestBody PasswordDTO passwordDTO) {
        ApiResult apiResult = userService.editPassword(id, passwordDTO);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/edit_profil/{id}")
    public HttpEntity<?> editProfil(@PathVariable UUID id,@Valid @RequestBody UserDTO userDTO) {
        ApiResult apiResult = userService.editProfil(id, userDTO);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }
}
