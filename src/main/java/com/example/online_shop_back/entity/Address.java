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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ADDRESS)
@SQLDelete(sql = "UPDATE " + EntityName.ADDRESS + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Address extends AbsEntity {

    @Column(name = "street")
    private String street;

    @Column(name = "home")
    private String home;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @ManyToOne
    private Region region;
}
