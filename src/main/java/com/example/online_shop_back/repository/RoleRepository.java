package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Role;
import com.example.online_shop_back.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleNameEnum nameEnum);
}
