package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Basket;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.projection.ProductProjection;
import com.example.online_shop_back.projection.ProductProjection1;
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

    @Query(nativeQuery = true, value = "select p.total_price                as productPrice,\n" +
            "       b.amount                     as amount,\n" +
            "       p.name                       as productName,\n" +
            "       p.discount_percent           as discountPercent,\n" +
            "       cast(p.detail_id as varchar) as detailId,\n" +
            "       cast(p.id as varchar)        as productId,\n" +
            "       mp.price                     as monthlyPrice,\n" +
            "       sum(b.amount * p.total_price)     as totalPrice\n" +
            "from product p\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "         inner join monthly_price mp on p.id = mp.product_id\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "where b.user_id = :userId\n" +
            "  and m.month = :month\n" +
            "group by p.total_price, b.amount, p.name, p.discount_percent, cast(p.detail_id as varchar), cast(p.id as varchar), mp.price")
    List<ProductProjection> getAllProductFromBasket(UUID userId, int month);

    @Query(nativeQuery = true, value = "select sum(mp.price)\n" +
            "from product p\n" +
            "         inner join monthly_price mp on p.id = mp.product_id\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "where b.user_id = :userId\n" +
            "  and m.month = :month")
    double getMonthlyPrice(UUID userId, int month);

    @Query(nativeQuery = true, value = "select sum(b.amount*p.total_price) as totalPrice\n" +
            "from product p\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "         inner join monthly_price mp on p.id = mp.product_id\n" +
            "         inner join months m on m.id = mp.month_id\n" +
            "where b.user_id = :userId\n" +
            "  and m.month = :month")
    double getTotalPrice(UUID userId, int month);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from basket\n" +
            "where user_id=:userId")
    void deleteByUserId(UUID userId);


    @Query(nativeQuery = true, value = "select p.total_price         as totalPrice,\n" +
            "       b.amount              as amount,\n" +
            "       p.name                as name,\n" +
            "       p.discount_percent    as discountPercent,\n" +
            "       cast(p.id as varchar) as productId\n" +
            "from product p\n" +
            "         inner join basket b on p.id = b.product_id\n" +
            "where b.user_id = :userId")
    List<ProductProjection1> getAllProductFromBasket(UUID userId);

    boolean existsByProduct(Product product);

}
