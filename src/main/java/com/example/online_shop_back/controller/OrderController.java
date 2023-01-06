package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.OrderDTO;
import com.example.online_shop_back.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/add_order")
    public HttpEntity<?> add(@RequestBody OrderDTO orderDTO) {
        ApiResult add = orderService.add(orderDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit_order_status/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, OrderDTO orderDTO){
        ApiResult apiResult = orderService.editStatus(id, orderDTO);
        return ResponseEntity.status(apiResult.getSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }
}
