package com.example.online_shop_back.controller;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.OrderDTO;
import com.example.online_shop_back.payload.OutputProductDTO;
import com.example.online_shop_back.service.OrderService;
import com.example.online_shop_back.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add_order")
    public HttpEntity<?> add(@RequestBody OrderDTO orderDTO, @CurrentUser User user) {
        ApiResult add = orderService.add(orderDTO, user);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit_order_status/{id}/{statusId}")
    public HttpEntity<?> edit(@PathVariable UUID id, @PathVariable UUID statusId) {
        ApiResult apiResult = orderService.editStatus(id, statusId);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResult);
    }

    @GetMapping("/get_all_order")
    public HttpEntity<?> getAll() {
        ApiResult all = orderService.getAll();
        return ResponseEntity.status(all.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

    @GetMapping("/get_all_order_by_userId/{id}")
    public HttpEntity<?> getByUserId(@PathVariable UUID id) {
        ApiResult allByUserId = orderService.getAllByUserId(id);
        return ResponseEntity.status(allByUserId.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(allByUserId);
    }

//    @PostMapping("/edit_status/{id}")
//    public HttpEntity<?> editStatus(@PathVariable UUID id){
//
//    }

}
