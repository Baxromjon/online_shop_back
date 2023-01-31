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

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.PRODUCT)
@SQLDelete(sql = "UPDATE " + EntityName.PRODUCT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Product extends AbsEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Measurement measurement;

    @Column(name = "price")
    private double price;

    private double totalPrice;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "description")
    private String description;

    @Column(name = "warranty_month")
    private int warrantyMonth;

    private boolean flash;// FLASH TAKLIFLAR UCHUN

    @ManyToOne
    private Brand brand;

    private boolean carousel; //HOME PAGEDA CAROUSELGA QO`YISH UCHUN

    @ManyToMany
    private List<Detail> detail;

    private boolean active;

    @ManyToMany
    @JoinTable(name = "product_photo",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id")})
    private List<Attachment> photo;

    @ManyToOne
    private Attachment mainPhoto;


}
