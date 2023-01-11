package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressDTO {
    private String fullName;
    private String phoneNumber;
    private UUID userId;
    private UUID orderId;
    private UUID regionId;
    private String district;
    private String home;
    private String street;
    private String address;
}
