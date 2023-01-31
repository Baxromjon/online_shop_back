package com.example.online_shop_back.controller;

import com.example.online_shop_back.repository.AttachmentContentRepository;
import com.example.online_shop_back.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import java.util.ArrayList;
import java.util.Arrays;
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

    @PostMapping("/multi-upload")
    public ResponseEntity multipleUpload(@RequestParam("files")MultipartFile[] files){
        List<Object> fileDownloadUrls=new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .forEach(file -> fileDownloadUrls.add(attachmentService.uploadFileMultiple(file)));
        return ResponseEntity.ok(fileDownloadUrls);
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
