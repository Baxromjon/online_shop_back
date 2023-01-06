package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.OrderStatusClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusClass, UUID> {
}
