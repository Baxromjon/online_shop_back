package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private UUID categoryId;
    private UUID measurementId;
    private double standardPrice;
    private double discountPercent;
    private String description;
    private UUID photoId;
//    private UUID monthlyPriceId;
    private int warrantyMonth;
    private UUID detailId;
    private double price;
    private boolean active;
}
