package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.WishList;
import com.example.online_shop_back.payload.ApiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UUID> {

    @Query(nativeQuery = true, value = "DELETE\n" +
            "from wish_list\n" +
            "where id = :id")
    void deleteWishListById(UUID id);

}
