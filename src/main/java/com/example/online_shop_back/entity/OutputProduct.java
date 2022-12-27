package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.OUTPUT_PRODUCT)
@SQLDelete(sql = "UPDATE " + EntityName.OUTPUT_PRODUCT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OutputProduct extends AbsEntity {

    @Column(name = "output_trade_id")
    @ManyToOne
    private OutputTrade outputTrade;

    @Column(name = "price")
    private double price;

    @Column(name = "monthly_price_id")
    @ManyToOne
    private MonthlyPrice monthlyPrice;

    @Column(name = "pay_type_id")
    @ManyToOne
    private PayType payType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

}
