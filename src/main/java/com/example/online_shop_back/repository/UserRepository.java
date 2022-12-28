package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Role;
import com.example.online_shop_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(String phoneNumber);
    Optional<User> findByPhoneNumberAndRolesIn(String phoneNumber, Set<Role> roles);

    Boolean existsByPhoneNumber(String phoneNumber);
}
