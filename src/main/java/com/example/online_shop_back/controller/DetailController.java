package com.example.online_shop_back.controller;

import com.example.online_shop_back.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detail")
public class DetailController {
    @Autowired
    DetailService detailService;


}
