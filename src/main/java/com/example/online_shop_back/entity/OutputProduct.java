package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
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
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.OUTPUT_PRODUCT)
@SQLDelete(sql = "UPDATE " + EntityName.OUTPUT_PRODUCT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OutputProduct extends AbsEntity {
//
//    @ManyToOne
//    private OutputTrade outputTrade;

    @Column(name = "price")
    private double totalPrice;

//    @ManyToMany
//    @JoinTable(name = "product_monthly_price",
//            joinColumns = {@JoinColumn(name = "product_id")},
//            inverseJoinColumns = {@JoinColumn(name = "monthly_price_id")})
//    private List<MonthlyPrice> monthlyPrices;

    @ManyToOne
    private PayType payType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private User user;

    @Column(name = "date")
    private Date date;

}
