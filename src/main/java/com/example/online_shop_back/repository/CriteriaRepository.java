package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, UUID> {
    Optional<Criteria> findByName(String name);
}
