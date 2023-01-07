package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.ProductDTO;
import com.example.online_shop_back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public HttpEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        ApiResult add = productService.add(productDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        ApiResult edit = productService.edit(id, productDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/getById/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id) {
        ApiResult byId = productService.getById(id);
        return ResponseEntity.status(byId.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(byId);
    }

    @GetMapping("/getAllProduct")
    public HttpEntity<?> getAll() {
        ApiResult all = productService.getAll();
        return ResponseEntity.status(all.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }
}
