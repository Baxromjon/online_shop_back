package com.example.online_shop_back.controller;

import com.example.online_shop_back.repository.AttachmentContentRepository;
import com.example.online_shop_back.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photo")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;


    @PostMapping("/add_photo")
    public HttpEntity<?> addPhoto(MultipartHttpServletRequest request) {
        UUID uuid = attachmentService.uploadFile(request);
        return ResponseEntity.ok(uuid);
    }


    @GetMapping("/get/{id}")
    public HttpEntity<?> getPhoto(@PathVariable UUID id){
        return attachmentService.getFile(id);
    }

    @GetMapping("/getAll")
    public List<?> getAllFile(){
        return attachmentService.getAllFile();
    }


}
