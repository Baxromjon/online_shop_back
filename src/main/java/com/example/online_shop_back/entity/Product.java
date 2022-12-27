package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.MEASUREMENT)
@SQLDelete(sql = "UPDATE " + EntityName.MEASUREMENT + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Product extends AbsEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "measurement_id")
    @ManyToOne
    private Measurement measurement;

    @Column(name = "price")
    private double price;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "description")
    private String description;

    @Column(name = "photo_id")
    @ManyToOne
    private Attachment attachment;

    @Column(name = "monthly_price_id")
    @ManyToOne
    private MonthlyPrice monthlyPrice;

    @Column(name = "warranty_month")
    private int warrantyMonth;

    @Column(name = "detail_id")
    @ManyToOne
    private Detail detail;

    @ManyToMany
    @JoinTable(name = "product_photo",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id")})
    private List<Attachment> photo;


}
