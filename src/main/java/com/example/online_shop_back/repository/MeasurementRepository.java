package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
    Optional <Measurement> findByName(String name);
}
