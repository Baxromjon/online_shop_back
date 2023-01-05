package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CurrencyTypeDTO;
import com.example.online_shop_back.service.CurrencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/currency_type")
public class CurrencyTypeController {
    @Autowired
    CurrencyTypeService currencyTypeService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody CurrencyTypeDTO currencyTypeDTO) {
        ApiResult add = currencyTypeService.add(currencyTypeDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody CurrencyTypeDTO currencyTypeDTO) {
        ApiResult edit = currencyTypeService.edit(id, currencyTypeDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }
}
