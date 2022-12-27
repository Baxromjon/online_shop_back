package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = EntityName.CURRENCY_TYPE)
@SQLDelete(sql = "UPDATE " + EntityName.CURRENCY_TYPE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class CurrencyType extends AbsEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
