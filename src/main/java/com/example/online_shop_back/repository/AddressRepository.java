package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE from address where id=:id")
    void deleteByAddressId(UUID id);
}
