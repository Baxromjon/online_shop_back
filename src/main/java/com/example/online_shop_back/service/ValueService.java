package com.example.online_shop_back.service;

import com.example.online_shop_back.repository.ValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueService {
    @Autowired
    ValueRepository valueRepository;
}
