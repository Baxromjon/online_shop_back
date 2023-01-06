package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Order;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.OrderDTO;
import com.example.online_shop_back.repository.OrderRepository;
import com.example.online_shop_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResult add(OrderDTO orderDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(orderDTO.getUserId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            Order order = new Order(
                    orderDTO.getTotalPrice(),
                    OrderStatus.NEW.toString(),
                    orderDTO.getTotalDiscountPrice(),
                    orderDTO.getDescription(),
                    userOptional.get(),
                    new Date()
            );
            orderRepository.save(order);
            return new ApiResult(true, "Order successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save order");
        }
    }

    public ApiResult editStatus(UUID id, OrderDTO orderDTO) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (!orderOptional.isPresent()) {
                return new ApiResult(false, "Order not found");
            }
            Order order = orderOptional.get();
            order.setOrderStatus(order.getOrderStatus());
            orderRepository.save(order);
            return new ApiResult(true, "Order status successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit order status");
        }
    }
}
