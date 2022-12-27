package com.example.online_shop_back.entity.template;

import com.example.online_shop_back.utils.ColumnName;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AbsEntity extends UserDateAudit {
    @Id
//    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = ColumnName.ID)
    private UUID id;
}
