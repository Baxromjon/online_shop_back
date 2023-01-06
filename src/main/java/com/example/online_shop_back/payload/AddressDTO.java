package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private UUID id;
    private UUID regionId;
    private String street;
    private String home;
    private String district;
    private String address;
}
