package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.enums.PayStatus;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PaymentDTO;
import com.example.online_shop_back.repository.*;
import com.example.online_shop_back.security.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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

    @Autowired
    OrderMonthRepository orderMonthRepository;

//    @Autowired
//    OrderMonthPaymentRepository orderMonthPaymentRepository;

    public ApiResult add(PaymentDTO paymentDTO) {
        try {
            PayType payType = payTypeRepository.findById(paymentDTO.getPayTypeId()).orElseThrow(() -> new ResourceNotFoundException("payType", "payTypeId", paymentDTO.getPayTypeId()));
            User user = userRepository.findById(paymentDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user", "userId", paymentDTO.getUserId()));
            Order order = orderRepository.findByOrderId(paymentDTO.getOrderId());
            Payment payment = new Payment(user, payType, paymentDTO.getAmount(), new Date(), paymentDTO.getDescription(), Collections.singletonList(order));
            paymentRepository.save(payment);

            OrderMonth orderMonth = orderMonthRepository.getByOrderId(paymentDTO.getOrderId());
            if (orderMonth == null) {
                return new ApiResult(false, "No such Order found!");
            }
            orderMonth.setPayment(payment);
            orderMonth.setPayStatus(PayStatus.PAID);
            orderMonth.setPaidPrice(paymentDTO.getAmount());
            orderMonth.setRemains(orderMonth.getPrice() > paymentDTO.getAmount() ?
                    (orderMonth.getPrice() - paymentDTO.getAmount()) : 0
            );
            orderMonthRepository.save(orderMonth);
            if (orderMonth.getRemains() > 0) {
                OrderMonth orderMonth1 = orderMonthRepository.getByOrderId(paymentDTO.getOrderId());
                orderMonth1.setPrice(orderMonth1.getPrice() + orderMonth.getRemains());
                orderMonthRepository.save(orderMonth1);
            }
//            if (paymentDTO.getAmount() > orderMonth.getPrice()) {
//                OrderMonth orderMonth1 = orderMonthRepository.getByOrderId(paymentDTO.getOrderId());
//                orderMonth1.setPrice(orderMonth1.getPrice() - (paymentDTO.getAmount() - orderMonth.getPrice()));
//                orderMonthRepository.save(orderMonth1);
//            }

            double remainPrice = orderMonthRepository.getRemainPrice(paymentDTO.getOrderId());
            if (remainPrice == 0) {
                return new ApiResult(true, "Congratulations! You have made all payments!");
            }
            return new ApiResult(true, "Successfully paid");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in payment");
        }
    }

    public ApiResult addFullPayment(PaymentDTO paymentDTO) {
        try {
            PayType payType = payTypeRepository.findById(paymentDTO.getPayTypeId()).orElseThrow();
            Optional<User> userOptional = userRepository.findById(paymentDTO.getUserId());
            Order order = orderRepository.findByOrderId(paymentDTO.getOrderId());
            Payment payment = new Payment(
                    userOptional.get(),
                    payType,
                    paymentDTO.getAmount(),
                    new Date(),
                    paymentDTO.getDescription(),
                    Collections.singletonList(order)
            );
            paymentRepository.save(payment);
            return new ApiResult(true, "Successfully add full payment");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in full payment");
        }
    }
}
