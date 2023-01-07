package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.WishListDTO;
import com.example.online_shop_back.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wish_list")
public class WishListController {
    @Autowired
    WishListService wishListService;

//    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody WishListDTO wishListDTO) {
        ApiResult add = wishListService.add(wishListDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResult delete = wishListService.delete(id);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }

    @GetMapping("/get_all_by_user/{id}")
    public HttpEntity<?> getAllByUser(@PathVariable UUID id){
        ApiResult allByUserId = wishListService.getAllByUserId(id);
        return ResponseEntity.status(allByUserId.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(allByUserId);
    }
}
