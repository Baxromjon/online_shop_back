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
@Entity(name = EntityName.DELIVERY_ADDRESS)
@SQLDelete(sql = "UPDATE " + EntityName.DELIVERY_ADDRESS + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class DeliveryAddress extends AbsEntity {

    private String fullName;

    private String phoneNumber;

    @ManyToOne
    private Region region;

    private String district;

    private String street;

    private String home;

    @ManyToOne
    private Order order;

    private String address;
}
