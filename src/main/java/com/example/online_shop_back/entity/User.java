package com.example.online_shop_back.entity;

import com.example.online_shop_back.entity.template.AbsEntity;
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
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = EntityName.USERS)
@SQLDelete(sql = "UPDATE " + EntityName.USERS + " SET deleted = TRUE WHERE id=?")
@Where(clause = "deleted=false")
public class User extends AbsEntity implements UserDetails {

    @Column(name = ColumnName.FIRST_NAME)
    private String firstName;

    @Column(name = ColumnName.LAST_NAME)
    private String lastName;

    @Column(name = ColumnName.PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = ColumnName.PASSWORD)
    private String password;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = ColumnName.EMAIL)
    private String email;

    @ManyToMany
    @JoinTable(name = "user_addresses",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id")})
    private List<Address> addresses;

    @Column(name = ColumnName.ACCOUNT_NON_EXPIRED)
    private boolean accountNonExpired = true;

    @Column(name = ColumnName.ACCOUNT_NON_LOCKED)
    private boolean accountNonLocked = true;

    @Column(name = ColumnName.CREDENTIALS_NON_EXPIRED)
    private boolean credentialsNonExpired = true;

    @Column(name = ColumnName.ENABLED)
    private boolean enabled = true;

    @ManyToMany
    @JoinTable(name = ColumnName.USER_ROLE,
            joinColumns = {@JoinColumn(name = ColumnName.USER_ID)},
            inverseJoinColumns = {@JoinColumn(name = ColumnName.ROLE_ID)})
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>(roles);
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    public User(String firstName, String lastName, String phoneNumber, String password, Set<Role> roles){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;

    }

}
