package com.example.online_shop_back.projection;

import java.util.UUID;

public interface ProductProjection {
    double getTotalPrice();
    double getProductPrice();
    double getAmount();
    String getProductName();
    double getDiscountPercent();
    UUID getDetailId();
    UUID getProductId();
    double getMonthlyPrice();
}
