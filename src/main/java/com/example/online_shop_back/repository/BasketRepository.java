package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Basket;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {
    Optional<Basket> findByProduct(Product product);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE from basket where id=:id")
    void deleteByBasketId(UUID id);

    @Query(nativeQuery = true, value = "select p.total_price      as productPrice,\n" +
            "       b.amount           as amount,\n" +
            "       p.name             as productName,\n" +
            "       p.discount_percent as discountPercent,\n" +
            "       p.detail_id        as detailId,\n" +
            "       p.id               as productId,\n" +
            "       mp.price           as monthlyPrice,\n" +
            "       b.amount*p.total_price as totalPrice\n" +
            "from product p\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "         inner join monthly_price mp on p.id = mp.product_id\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "where b.user_id = :userId\n" +
            "and m.month=:month")
    List<ProductProjection> getAllProductFromBasket(UUID userId, int month);

    @Query(value = "select sum(p.total_price) from basket b\n" +
            "                   inner join product p on p.id = b.product_id\n" +
            "where user_id=:userId", nativeQuery = true)
    double getTotalPrice(UUID userId);


    @Query(nativeQuery = true, value = "select sum(mp.price)\n" +
            "from product p\n" +
            "         inner join monthly_price mp on p.id = mp.product_id\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "where b.user_id = :userId\n" +
            "  and m.month = :month")
    double getMonthlyPrice(UUID userId, int month);
}
