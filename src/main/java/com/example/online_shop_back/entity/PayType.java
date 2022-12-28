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
@Entity(name = EntityName.PAY_TYPE)
@SQLDelete(sql = "UPDATE " + EntityName.PAY_TYPE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class PayType extends AbsEntity {

    @ManyToOne
    private CurrencyType currencyType;

    @Column(name = "name")
    private String name;
}
