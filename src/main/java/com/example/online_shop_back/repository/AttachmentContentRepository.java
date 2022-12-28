package com.example.online_shop_back.repository;

import com.example.online_shop_back.entity.Attachment;
import com.example.online_shop_back.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

    Optional<AttachmentContent> findByAttachmentId(UUID attachment_id);
}
