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
@Entity(name = EntityName.RATE)
@SQLDelete(sql = "UPDATE " + EntityName.RATE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Rate extends AbsEntity {

    @Column(name = "star_count")
    private int starCount;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Criteria criteria;
}
