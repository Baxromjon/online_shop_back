package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Category;
import com.example.online_shop_back.entity.Measurement;
import com.example.online_shop_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from product where id=:productId")
    void deleteProductById(UUID productId);
}
