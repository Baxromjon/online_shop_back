package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Order;
import com.example.online_shop_back.entity.OrderMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMonthRepository extends JpaRepository<OrderMonth, UUID> {

    @Query(nativeQuery = true, value = "select order_month.*\n" +
            "from order_month\n" +
            "inner join orders o on o.id = order_month.order_id\n" +
            "where o.order_id = :orderId\n" +
            "  and pay_status = 'UNPAID'\n" +
            "order by deadline\n" +
            "limit 1")
    OrderMonth getByOrderId(String orderId);

    @Query(nativeQuery = true, value = "select sum(om.price) from order_month om\n" +
            "inner join orders o on o.id = om.order_id\n" +
            "where o.order_id=:orderId\n" +
            "and pay_status='UNPAID'")
    double getRemainPrice(String orderId);

    @Query(nativeQuery = true, value = "select sum(order_month.price)\n" +
            "from order_month\n" +
            "inner join orders o on o.id = order_month.order_id\n" +
            "where o.order_id = :orderId\n" +
            "and pay_status='UNPAID'")
    double getUnPaidPrice(String orderId);
}
