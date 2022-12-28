package com.example.online_shop_back.entity.template;

import com.example.online_shop_back.utils.ColumnName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    @Column(name = ColumnName.CREATED_BY_ID, updatable = false)
    private UUID createdById;

    @LastModifiedBy
    @Column(name = ColumnName.UPDATED_BY_ID)
    private UUID updatedById;

}
