package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MonthlyPriceDTO;
import com.example.online_shop_back.service.MonthlyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/monthly_price")
public class MonthlyPriceController {
    @Autowired
    MonthlyPriceService monthService;

    @PostMapping("/add")
    public HttpEntity<?> addMonth(@RequestBody MonthlyPriceDTO monthDTO) {
        ApiResult add = monthService.add(monthDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody MonthlyPriceDTO monthDTO) {
        ApiResult edit = monthService.edit(id, monthDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/get_all_monthly_price")
    public HttpEntity<?> getAll() {
        ApiResult all = monthService.getAll();
        return ResponseEntity.status(all.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }
}
