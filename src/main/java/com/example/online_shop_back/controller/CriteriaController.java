package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CriteriaDTO;
import com.example.online_shop_back.service.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/criteria")
public class CriteriaController {
    @Autowired
    CriteriaService criteriaService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody CriteriaDTO criteriaDTO) {
        ApiResult add = criteriaService.add(criteriaDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }


    @PostMapping("/edit/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody CriteriaDTO criteriaDTO) {
        ApiResult edit = criteriaService.edit(id, criteriaDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }
}
