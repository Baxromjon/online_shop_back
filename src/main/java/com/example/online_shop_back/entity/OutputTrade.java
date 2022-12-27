package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
import com.example.online_shop_back.utils.EntityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.OUTPUT_TRADE)
@SQLDelete(sql = "UPDATE " + EntityName.OUTPUT_TRADE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class OutputTrade extends AbsEntity {

    @Column(name = "client_id")
    @ManyToOne
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

}
