package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Month;
import com.example.online_shop_back.entity.MonthlyPrice;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.projection.ProductProjection2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MonthlyPriceRepository extends JpaRepository<MonthlyPrice, UUID> {
    boolean existsByProductAndMonth(Product product, Month month);

    @Query(nativeQuery = true, value = "select m.month as month, mp.price as productPrice, p.name as productName\n" +
            "from monthly_price mp\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "         inner join product p on p.id = mp.product_id\n" +
            "where p.id = :productId")
    List<ProductProjection2> getAllProductByMonthPrice(UUID productId);
}
