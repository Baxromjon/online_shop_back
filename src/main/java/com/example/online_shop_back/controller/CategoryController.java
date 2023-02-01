package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CategoryDTO;
import com.example.online_shop_back.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/add_category")
    public HttpEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        ApiResult apiResult = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/edit_category/{id}")
    public HttpEntity<?> editCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
        ApiResult edit = categoryService.edit(id, categoryDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/get_all_category")
    public HttpEntity<?> getAll() {
        ApiResult all = categoryService.getAll();
        return ResponseEntity.status(all.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @DeleteMapping("/delete_category/{categoryId}")
    public HttpEntity<?> delete(@PathVariable UUID categoryId) {
        ApiResult delete = categoryService.delete(categoryId);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }
}
