package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.BasketDTO;
import com.example.online_shop_back.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    @Autowired
    BasketService basketService;

    @PostMapping("/add_to_basket")
    public HttpEntity<?> add(@RequestBody BasketDTO basketDTO) {
        ApiResult add = basketService.add(basketDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit_basket_product/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody BasketDTO basketDTO) {
        ApiResult edit = basketService.edit(id, basketDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @DeleteMapping("/delete_product_basket/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResult delete = basketService.delete(id);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }
}
