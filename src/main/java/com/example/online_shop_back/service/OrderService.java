package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.enums.PayStatus;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.OrderDTO;
import com.example.online_shop_back.payload.OutputProductDTO;
import com.example.online_shop_back.projection.ProductProjection;
import com.example.online_shop_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderMonthRepository orderMonthRepository;

    @Autowired
    MonthRepository monthRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    AddressRepository addressRepository;

    public ApiResult add(OrderDTO orderDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(orderDTO.getUserId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            String orderId = randomOrderId();
            boolean exists = orderRepository.existsByOrderId(orderId);
            if (exists) {
                return new ApiResult(false, "OrderId exists");
            }
            Order order = new Order();
            order.setOrderStatus(OrderStatus.NEW.toString());
            order.setDescription(orderDTO.getDescription());
            order.setUser(userOptional.get());
            order.setOrderId(orderId);
            order.setDate(new Date());
            orderRepository.save(order);
            Month month = monthRepository.findById(orderDTO.getMonthId()).orElseThrow();
            List<ProductProjection> allProductFromBasket = basketRepository.getAllProductFromBasket(orderDTO.getUserId(), month.getMonth());
            List<OutputProduct> outputProducts = new ArrayList<>();
            double totalPrice = 0;
            for (int i = 0; i < allProductFromBasket.size(); i++) {
                OutputProduct outputProduct = new OutputProduct();
                outputProduct.setOrder(order);
                outputProduct.setProduct(productRepository.findById(allProductFromBasket.get(i).getProductId()).orElseThrow());
                outputProduct.setAmount(allProductFromBasket.get(i).getAmount());
                outputProducts.add(outputProduct);
                totalPrice += allProductFromBasket.get(i).getTotalPrice();
            }

            outputProductRepository.saveAll(outputProducts);

            List<OrderMonth> orderMonths = new ArrayList<>();

            double monthlyPrice = basketRepository.getMonthlyPrice(orderDTO.getUserId(), month.getMonth());
            order.setTotalPrice(totalPrice);
            Date date = new Date();
            for (int i = 0; i < month.getMonth(); i++) {
                OrderMonth orderMonth = new OrderMonth();
                orderMonth.setOrder(order);
                orderMonth.setPayStatus(PayStatus.UNPAID);
                orderMonth.setPrice(0);
                orderMonth.setDeadline(new Date(date.getYear(), date.getMonth()+i, date.getDay()));
                orderMonths.add(orderMonth);
                orderMonth.setPrice(monthlyPrice);
            }
            orderMonthRepository.saveAll(orderMonths);
            orderRepository.save(order);
            basketRepository.deleteByUserId(orderDTO.getUserId());
            return new ApiResult(true, "Order successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save order");
        }
    }

    public ApiResult editStatus(UUID id, UUID statusId) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (!orderOptional.isPresent()) {
                return new ApiResult(false, "Order not found");
            }
            OrderStatusClass orderStatusClass = orderStatusRepository.findById(statusId).orElseThrow();
            Order order = orderOptional.get();
            order.setOrderStatus(orderStatusClass.getOrderStatus().toString());
            orderRepository.save(order);
            return new ApiResult(true, "Order status successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit order status");
        }
    }

    public ApiResult getAll() {
        try {
            List<Order> all = orderRepository.findAll();
            return new ApiResult(all, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Orders");
        }
    }

    public ApiResult getAllByUserId(UUID id) {
        try {
            List<Order> allOrdersByUserId = orderRepository.getAllOrdersByUserId(id);
            return new ApiResult(allOrdersByUserId, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Orders by User");
        }
    }

    public String randomOrderId() {
        int max = 9999999;
        int min = 1000000;
        int floor = (int) Math.floor(Math.random() * (max - min + 1) + min);
        Random rnd = new Random();
        final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder alphabat = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            alphabat.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        alphabat.append(floor);
        return alphabat.toString();
    }

    public static Date convertToDateUsingDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }


}
