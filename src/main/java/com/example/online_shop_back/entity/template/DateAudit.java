package com.example.online_shop_back.entity.template;

import com.example.online_shop_back.utils.ColumnName;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class DateAudit implements Serializable {

    @CreationTimestamp
    @Column(name = ColumnName.CREATED_AT, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = ColumnName.UPDATED_AT)
    private Timestamp updatedAt;

    @Column(name = ColumnName.DELETED)
    private Boolean deleted = false;
}
