package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.ValueDTO;
import com.example.online_shop_back.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/value")
public class ValueController {
    @Autowired
    ValueService valueService;


    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody ValueDTO valueDTO) {
        ApiResult add = valueService.add(valueDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody ValueDTO valueDTO) {
        ApiResult edit = valueService.edit(id, valueDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/get_all_value")
    public HttpEntity<?> getAll(){
        ApiResult all = valueService.getAll();
        return ResponseEntity.status(all.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

}
