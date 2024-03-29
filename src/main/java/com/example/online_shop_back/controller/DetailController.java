package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.DetailDTO;
import com.example.online_shop_back.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/detail")
public class DetailController {
    @Autowired
    DetailService detailService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody DetailDTO detailDTO) {
        ApiResult add = detailService.add(detailDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody DetailDTO detailDTO) {
        ApiResult edit = detailService.edit(id, detailDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/get_all_details")
    public HttpEntity<?> getAll(){
        ApiResult all = detailService.getAll();
        return ResponseEntity.status(all.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

}
