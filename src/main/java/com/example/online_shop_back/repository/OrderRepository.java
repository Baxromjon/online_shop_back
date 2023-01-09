package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {


    @Query(nativeQuery = true, value = "select * from orders\n" +
            "where user_id=:id")
    List<Order> getAllOrdersByUserId(UUID id);

    boolean existsByOrderId(String orderId);

    Order findByOrderId(String orderId);
}
