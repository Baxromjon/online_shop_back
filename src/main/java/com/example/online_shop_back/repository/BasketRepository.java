package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Basket;
import com.example.online_shop_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {
    Optional<Basket> findByProduct(Product product);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE from basket where id=:id")
    void deleteByBasketId(UUID id);
}
