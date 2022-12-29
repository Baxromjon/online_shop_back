package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.MonthlyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonthlyPriceRepository extends JpaRepository<MonthlyPrice, UUID> {
}
