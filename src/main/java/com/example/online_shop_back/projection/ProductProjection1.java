package com.example.online_shop_back.projection;

import java.util.UUID;

public interface ProductProjection1 {
    double getTotalPrice();

    int getAmount();

    String getName();

    double getDiscountPercent();

    UUID getProductId();
}
