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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.WISHLIST)
@SQLDelete(sql = "UPDATE " + EntityName.WISHLIST + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class WishList extends AbsEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;
}
