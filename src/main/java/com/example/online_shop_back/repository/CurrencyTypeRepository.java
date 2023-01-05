package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, UUID> {
    Optional<CurrencyType> findByName(String name);

}
