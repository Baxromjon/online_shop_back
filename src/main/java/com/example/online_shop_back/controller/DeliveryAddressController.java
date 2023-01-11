package com.example.online_shop_back.controller;

import com.example.online_shop_back.entity.Address;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.DeliveryAddressDTO;
import com.example.online_shop_back.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/delivery_address")
public class DeliveryAddressController {
    @Autowired
    DeliveryAddressService deliveryAddressService;

    @PostMapping("/add_delivery_address")
    public HttpEntity<?> addAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) {
        ApiResult add = deliveryAddressService.add(deliveryAddressDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/add_delivery_address_from_address/{userId}/{orderId}")
    public HttpEntity<?> addDeliveryAddress(@PathVariable UUID userId, @PathVariable UUID orderId, @RequestBody Address address) {
        ApiResult apiResult = deliveryAddressService.addDeliveryAddress(userId, orderId, address);
        return ResponseEntity.status(apiResult.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

}
