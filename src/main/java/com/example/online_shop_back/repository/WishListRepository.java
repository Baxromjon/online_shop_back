package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UUID> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE\n" +
            "from wish_list\n" +
            "where id = :id")
    void deleteWishListById(UUID id);

    List<WishList> findAllByUser(User user);
}
