package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputProductDTO {
    private List<UUID> productId;
//    private UUID orderId;
    private double amount;
}
