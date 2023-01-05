package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.CurrencyType;
import com.example.online_shop_back.entity.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayTypeRepository extends JpaRepository<PayType, UUID> {
    Optional<PayType> findByNameAndCurrencyType(String name, CurrencyType currencyType);
}
