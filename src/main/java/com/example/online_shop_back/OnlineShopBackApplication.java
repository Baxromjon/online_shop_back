package com.example.online_shop_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OnlineShopBackApplication {

    @GetMapping("/message")
    public String message(){
        return "Congrats! Successfully deployed";
    }
    public static void main(String[] args) {
        SpringApplication.run(OnlineShopBackApplication.class, args);
    }

}
