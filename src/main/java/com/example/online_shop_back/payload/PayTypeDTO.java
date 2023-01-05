package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayTypeDTO {
    private UUID id;
    private String name;
    private UUID currencyTypeId;
}
