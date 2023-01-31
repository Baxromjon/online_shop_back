package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Attachment;
import com.example.online_shop_back.entity.AttachmentContent;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.repository.AttachmentContentRepository;
import com.example.online_shop_back.repository.AttachmentRepository;
import com.example.online_shop_back.security.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public UUID uploadFile(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("file");
            assert file != null;
            Attachment attachment = attachmentRepository.save(new Attachment(
                    file.getOriginalFilename(), file.getSize(), file.getContentType()
            ));
            try {
                attachmentContentRepository.save(new AttachmentContent(attachment, file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return attachment.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpEntity<?> getFile(UUID id) {
        try {
            Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("attachment", "id", id));
            AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(id).orElseThrow(() -> new ResourceNotFoundException("attachmentContent", "attachmentId", id));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(attachment.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + attachment.getName() + "\"")
                    .body(attachmentContent.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<?> getAllFile() {
        return attachmentRepository.findAll();
    }
    public UUID uploadFileMultiple(MultipartFile file){
        try {
            assert file != null;
            Attachment attachment = attachmentRepository.save(new Attachment(
                    file.getOriginalFilename(), file.getSize(), file.getContentType()
            ));
            try {
                attachmentContentRepository.save(new AttachmentContent(attachment, file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return attachment.getId();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
