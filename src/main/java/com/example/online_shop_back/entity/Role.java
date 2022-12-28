package com.example.online_shop_back.entity;

import com.example.online_shop_back.enums.RoleNameEnum;
import com.example.online_shop_back.utils.ColumnName;
import com.example.online_shop_back.utils.EntityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.ROLE)
@SQLDelete(sql = "UPDATE " + EntityName.ROLE + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ColumnName.NAME, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleNameEnum name;

    public Role(RoleNameEnum roleNameEnum){
        this.name=roleNameEnum;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
