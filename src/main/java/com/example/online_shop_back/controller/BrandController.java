package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.BrandDTO;
import com.example.online_shop_back.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    BrandService brandService;

    @PostMapping("/add_brand")
    public HttpEntity<?> add(@RequestBody BrandDTO brandDTO) {
        ApiResult add = brandService.add(brandDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit_brand/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody BrandDTO brandDTO) {
        ApiResult edit = brandService.edit(id, brandDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(edit);
    }


    @DeleteMapping("/delete_brand/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResult delete = brandService.delete(id);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }

    @GetMapping("/getAll")
    public HttpEntity<?> getAll(){
        ApiResult all = brandService.getAll();
        return ResponseEntity.status(all.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }


}
