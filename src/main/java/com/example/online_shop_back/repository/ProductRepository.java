package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Category;
import com.example.online_shop_back.entity.Measurement;
import com.example.online_shop_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByNameAndCategoryAndMeasurement(String name, Category category, Measurement measurement);

    @Query(nativeQuery = true, value = "select * from product where category_id=:id")
    List<Product> getAllByCategory(UUID id);


    @Query(nativeQuery = true, value = "select * from product where flash=true")
    List<Product> getAllByFlashIsTrue();
    @Query(nativeQuery = true, value = "select * from product where carousel=true")
    List<Product> getAllByCarouselIsTrue();
}
