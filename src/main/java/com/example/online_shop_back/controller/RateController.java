package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RateDTO;
import com.example.online_shop_back.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rate")
public class RateController {
    @Autowired
    RateService rateService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody RateDTO rateDTO) {
        ApiResult add = rateService.add(rateDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody RateDTO rateDTO) {
        ApiResult edit = rateService.edit(id, rateDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }


}
