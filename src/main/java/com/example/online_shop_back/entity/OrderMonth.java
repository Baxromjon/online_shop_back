package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.enums.PayStatus;
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
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ORDER_MONTH)
@SQLDelete(sql = "UPDATE " + EntityName.ORDER_MONTH + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OrderMonth extends AbsEntity {

    @ManyToOne
    private Order order;

    private double price;

    private Date deadline;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne
    private Payment payment;



}
