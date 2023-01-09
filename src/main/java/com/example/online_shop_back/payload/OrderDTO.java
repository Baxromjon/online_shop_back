package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private String description;
    private UUID userId;
//    private String orderStatus;
//    private List<UUID> productId;
    //    private UUID orderId;
//    private double amount;
    private UUID monthId;

}
