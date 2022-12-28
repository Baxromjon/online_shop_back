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


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.OUTPUT_PRODUCT)
@SQLDelete(sql = "UPDATE " + EntityName.OUTPUT_PRODUCT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OutputProduct extends AbsEntity {

    @ManyToOne
    private OutputTrade outputTrade;

    @Column(name = "price")
    private double price;

    @ManyToOne
    private MonthlyPrice monthlyPrice;

    @ManyToOne
    private PayType payType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

}
