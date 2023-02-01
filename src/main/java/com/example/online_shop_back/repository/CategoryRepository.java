package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from category where id=:categoryId")
    void deleteByCategoryId(UUID categoryId);
}
