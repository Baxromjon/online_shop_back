package com.example.online_shop_back.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Configuration
public class BeanFactory {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
