package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.enums.OrderStatus;
import com.example.online_shop_back.enums.OrderTypeEnum;
import com.example.online_shop_back.enums.PayStatus;
import com.example.online_shop_back.enums.PaymentTypeEnum;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.OrderDTO;
import com.example.online_shop_back.payload.PaymentDTO;
import com.example.online_shop_back.projection.ProductProjection;
import com.example.online_shop_back.projection.ProductProjection1;
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

    @Autowired
    OrderTypeRepository orderTypeRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    PayTypeRepository payTypeRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RegionRepository regionRepository;

    public ApiResult add(OrderDTO orderDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(orderDTO.getUserId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            OrderType orderType = orderTypeRepository.findById(orderDTO.getOrderTypeId()).orElseThrow();
            PaymentType paymentType = paymentTypeRepository.findById(orderDTO.getPaymentTypeId()).orElseThrow();
            PayType payType = payTypeRepository.findById(orderDTO.getPayTypeId()).orElseThrow();
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
            order.setPaymentType(paymentType);
            order.setOrderType(orderType);
            orderRepository.save(order);

            if (paymentType.getPaymentTypeEnum() == PaymentTypeEnum.INSTALLMENT_PAYMENT) {
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
                    totalPrice += allProductFromBasket.get(i).getMonthlyPrice();
                }

                outputProductRepository.saveAll(outputProducts);

                double monthlyPrice = basketRepository.getMonthlyPrice(orderDTO.getUserId(), month.getMonth());
                List<OrderMonth> orderMonths = new ArrayList<>();
                Date date = new Date();
                order.setTotalPrice(totalPrice * month.getMonth());
                for (int i = 0; i < month.getMonth(); i++) {
                    OrderMonth orderMonth = new OrderMonth();
                    orderMonth.setOrder(order);
                    orderMonth.setPayStatus(PayStatus.UNPAID);
                    orderMonth.setPrice(0);
                    orderMonth.setDeadline(new Date(date.getYear(), date.getMonth() + i, date.getDay()));
                    orderMonths.add(orderMonth);
                    orderMonth.setPrice(monthlyPrice);
                }
                orderMonthRepository.saveAll(orderMonths);
                if (orderType.getOrderTypeEnum() == OrderTypeEnum.DELIVERY) {
                    DeliveryAddress deliveryAddress = new DeliveryAddress();
                    deliveryAddress.setOrder(order);
                    deliveryAddress.setFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
                    deliveryAddress.setPhoneNumber(userOptional.get().getPhoneNumber());
                    if (orderDTO.getAddressId() != null) {
                        Address address = addressRepository.findById(orderDTO.getAddressId()).orElseThrow();
                        deliveryAddress.setRegion(address.getRegion());
                        deliveryAddress.setDistrict(address.getDistrict());
                        deliveryAddress.setHome(address.getHome());
                        deliveryAddress.setStreet(address.getStreet());
                        deliveryAddress.setAddress(address.getAddress());
                    } else {
                        Region region = regionRepository.findById(orderDTO.getRegionId()).orElseThrow();
                        deliveryAddress.setAddress(orderDTO.getAddress());
                        deliveryAddress.setRegion(region);
                        deliveryAddress.setDistrict(orderDTO.getDistrict());
                        deliveryAddress.setHome(orderDTO.getHome());
                        deliveryAddress.setStreet(orderDTO.getStreet());
                    }
                    deliveryAddressRepository.save(deliveryAddress);
                }
            }
            else if (paymentType.getPaymentTypeEnum() == PaymentTypeEnum.FULL) {
                List<ProductProjection1> allProductFromBasket = basketRepository.getAllProductFromBasket(orderDTO.getUserId());
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
                order.setTotalPrice(totalPrice);
                orderRepository.save(order);
                outputProductRepository.saveAll(outputProducts);

                PaymentDTO paymentDTO = new PaymentDTO(
                        userOptional.get().getId(),
                        orderDTO.getPayTypeId(),
                        totalPrice,
                        "to`liq to`lov",
                        order.getOrderId()
                );
                ApiResult add = paymentService.addFullPayment(paymentDTO);
                if (add.getSuccess()) {
                    order.setOrderStatus(OrderStatus.PAID.toString());
                    if (orderType.getOrderTypeEnum() == OrderTypeEnum.DELIVERY) {
                        DeliveryAddress deliveryAddress = new DeliveryAddress();
                        deliveryAddress.setOrder(order);
                        deliveryAddress.setFullName(userOptional.get().getFirstName() + " " + userOptional.get().getLastName());
                        deliveryAddress.setPhoneNumber(userOptional.get().getPhoneNumber());
                        if (orderDTO.getAddressId() != null) {
                            Address address = addressRepository.findById(orderDTO.getAddressId()).orElseThrow();
                            deliveryAddress.setRegion(address.getRegion());
                            deliveryAddress.setDistrict(address.getDistrict());
                            deliveryAddress.setHome(address.getHome());
                            deliveryAddress.setStreet(address.getStreet());
                            deliveryAddress.setAddress(address.getAddress());
                        } else {
                            Region region = regionRepository.findById(orderDTO.getRegionId()).orElseThrow();
                            deliveryAddress.setAddress(orderDTO.getAddress());
                            deliveryAddress.setRegion(region);
                            deliveryAddress.setDistrict(orderDTO.getDistrict());
                            deliveryAddress.setHome(orderDTO.getHome());
                            deliveryAddress.setStreet(orderDTO.getStreet());
                        }
                        deliveryAddressRepository.save(deliveryAddress);
                    }
                }
            }


//            List<ProductProjection1> projection1List = basketRepository.getAllProductFromBasket(orderDTO.getUserId());
//            double totalPrice = 0;
//            for (int i = 0; i < projection1List.size(); i++) {
//                totalPrice += (projection1List.get(i).getTotalPrice() * projection1List.get(i).getAmount());
//            }
//            order.setTotalPrice(totalPrice);
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
