package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.utils.EntityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ORDER_STATUS)
@SQLDelete(sql = "UPDATE " + EntityName.ORDER_STATUS + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OrderStatusClass extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
