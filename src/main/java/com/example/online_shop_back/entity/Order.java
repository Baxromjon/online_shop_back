package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.utils.ColumnName;
import com.example.online_shop_back.utils.EntityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ORDER)
@SQLDelete(sql = "UPDATE " + EntityName.ORDER + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Order extends AbsEntity {

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = ColumnName.STATUS, unique = true)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "total_discount_price")
    private double totalDiscountPrice;

    @OneToOne
    private OutputTrade outputTrade;
}
