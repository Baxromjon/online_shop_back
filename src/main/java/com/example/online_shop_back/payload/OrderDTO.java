package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private double totalPrice;
    private double totalDiscountPrice;
    private String description;
    private UUID userId;
    private String orderStatus;

}
