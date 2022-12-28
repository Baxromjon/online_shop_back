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

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.MONTHLY_PRICE)
@SQLDelete(sql = "UPDATE " + EntityName.MONTHLY_PRICE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class MonthlyPrice extends AbsEntity {

    @Column(name = "month")
    private String month;

    @Column(name = "price")
    private Double price;
}
