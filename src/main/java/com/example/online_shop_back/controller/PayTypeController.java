package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PayTypeDTO;
import com.example.online_shop_back.service.PayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pay_type")
public class PayTypeController {
    @Autowired
    PayTypeService payTypeService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody PayTypeDTO payTypeDTO) {
        ApiResult add = payTypeService.add(payTypeDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }


    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody PayTypeDTO payTypeDTO) {
        ApiResult edit = payTypeService.edit(id, payTypeDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

}
