package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.ColumnName;
import com.example.online_shop_back.utils.EntityName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    @Column(name = "price")
//    private double totalPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

//    @ManyToOne
//    private MonthlyPrice monthlyPrice;

    @Column(name = "amount")
    private double amount;

}
