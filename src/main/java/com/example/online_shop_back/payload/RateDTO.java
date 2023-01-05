package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    private UUID id;
    private int starCount;
    private UUID productId;
    private UUID criteriaId;
}
