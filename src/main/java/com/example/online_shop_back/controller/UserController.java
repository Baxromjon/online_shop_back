package com.example.online_shop_back.controller;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PasswordDTO;
import com.example.online_shop_back.payload.UserDTO;
import com.example.online_shop_back.service.UserService;
import com.example.online_shop_back.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public HttpEntity<?> getUser(@CurrentUser User user) {
        return ResponseEntity.ok(new ApiResult(user, true, "Success"));
    }

    @PostMapping("/edit_password/{id}")
    public HttpEntity<?> editPassword(@PathVariable UUID id, @Valid @RequestBody PasswordDTO passwordDTO) {
        ApiResult apiResult = userService.editPassword(id, passwordDTO);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/edit_profil/{id}")
    public HttpEntity<?> editProfil(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
        ApiResult apiResult = userService.editProfil(id, userDTO);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/edit_user_role/{id}/{roleId}")
    public HttpEntity<?> editRole(@PathVariable UUID id, @PathVariable Integer roleId) {
        ApiResult apiResult = userService.editRole(id, roleId);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/add_role_to_user/{id}/{roleId}")
    public HttpEntity<?> addRole(@PathVariable UUID id, @PathVariable Integer roleId){
        ApiResult apiResult = userService.addRoleToUser(id, roleId);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @GetMapping("/get_all_user")
    public HttpEntity<?> getAllUser(){
        ApiResult allUser = userService.getAllUser();
        return ResponseEntity.status(allUser.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(allUser);
    }
}
