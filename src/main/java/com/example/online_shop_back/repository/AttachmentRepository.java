package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    @Query(nativeQuery = true, value = "select a.* from attachment a\n" +
            "inner join product_photo pp on a.id = pp.photo_id\n" +
            "inner join product p on p.id = pp.product_id\n" +
            "where p.id=:id\n" +
            "limit 1")
    Attachment getFirstPhotoId(UUID id);

}
