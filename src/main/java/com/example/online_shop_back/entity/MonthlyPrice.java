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

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.MONTHLY_PRICE)
@SQLDelete(sql = "UPDATE " + EntityName.MONTHLY_PRICE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class MonthlyPrice extends AbsEntity {

//    @ManyToMany
//    @JoinTable(name = "product_monthly_price",
//            joinColumns = {@JoinColumn(name = "product_id")},
//            inverseJoinColumns = {@JoinColumn(name = "monthly_price_id")})
//    private List<Product> product;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Month month;

    @Column(name = "price")
    private Double price;
}
