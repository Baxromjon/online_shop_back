package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.OutputProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutputProductRepository extends JpaRepository<OutputProduct, UUID> {

    @Query(nativeQuery = true, value = "select sum(product.total_price) from product\n" +
            "inner join output_product op on product.id = op.product_id\n" +
            "where op.order_id=:orderId")
    double getAllProductPrice(UUID orderId);
}
