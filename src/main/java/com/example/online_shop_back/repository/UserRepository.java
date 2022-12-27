package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(String phoneNumber);
}
