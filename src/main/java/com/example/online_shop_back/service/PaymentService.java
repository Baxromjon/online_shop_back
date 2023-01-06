package com.example.online_shop_back.service;

import com.example.online_shop_back.repository.OrderRepository;
import com.example.online_shop_back.repository.PayTypeRepository;
import com.example.online_shop_back.repository.PaymentRepository;
import com.example.online_shop_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PayTypeRepository payTypeRepository;

    @Autowired
    OrderRepository orderRepository;
}
