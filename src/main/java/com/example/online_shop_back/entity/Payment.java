package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.PAYMENT)
@SQLDelete(sql = "UPDATE " + EntityName.PAYMENT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Payment extends AbsEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private PayType payType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "pay_date")
    private Date date;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Order order;
}
