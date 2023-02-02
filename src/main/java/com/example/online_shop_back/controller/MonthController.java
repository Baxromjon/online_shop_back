package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MonthDTO;
import com.example.online_shop_back.service.MonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/month")
public class MonthController {
    @Autowired
    MonthService monthService;

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody MonthDTO monthDTO) {
        ApiResult add = monthService.add(monthDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }


    @PostMapping("/edit/{id}")
    public HttpEntity<?> editMonth(@PathVariable UUID id, @RequestBody MonthDTO monthDTO) {
        ApiResult edit = monthService.edit(id, monthDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @GetMapping("/get_all_month")
    public HttpEntity<?> getAll() {
        ApiResult all = monthService.getAll();
        return ResponseEntity.status(all.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @DeleteMapping("/delete_month/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResult delete = monthService.delete(id);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }

}
