package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.DeliveryAddressDTO;
import com.example.online_shop_back.repository.DeliveryAddressRepository;
import com.example.online_shop_back.repository.OrderRepository;
import com.example.online_shop_back.repository.RegionRepository;
import com.example.online_shop_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryAddressService {
    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RegionRepository regionRepository;

    public ApiResult add(DeliveryAddressDTO deliveryAddressDTO) {
        try{
            Optional<User> userOptional = userRepository.findById(deliveryAddressDTO.getUserId());
            if (!userOptional.isPresent()){
                return new ApiResult(false, "User not found");
            }
            Optional<Order> orderOptional = orderRepository.findById(deliveryAddressDTO.getOrderId());
            if (!orderOptional.isPresent()){
                return new ApiResult(false, "Order not found");
            }
            Optional<Region> regionOptional = regionRepository.findById(deliveryAddressDTO.getRegionId());
            if (!regionOptional.isPresent()){
                return new ApiResult(false, "Region not found");
            }
            DeliveryAddress deliveryAddress=new DeliveryAddress(
                    userOptional.get().getFirstName()+" "+userOptional.get().getLastName(),
                    userOptional.get().getPhoneNumber(),
                    regionOptional.get(),
                    deliveryAddressDTO.getDistrict(),
                    deliveryAddressDTO.getStreet(),
                    deliveryAddressDTO.getHome(),
                    orderOptional.get(),
                    deliveryAddressDTO.getAddress()
            );
            deliveryAddressRepository.save(deliveryAddress);
            return new ApiResult(true, "Address successfully added");
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in add delivery address");
        }
    }

    public ApiResult addDeliveryAddress(UUID userId, UUID orderId, Address address) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()){
                return new ApiResult(false, "User not found");
            }
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()){
                return new ApiResult(false, "Order not found");
            }
            Optional<Region> regionOptional = regionRepository.findById(address.getRegion().getId());
            if (!regionOptional.isPresent()){
                return new ApiResult(false, "Region not found");
            }
            DeliveryAddress deliveryAddress=new DeliveryAddress(
                    userOptional.get().getFirstName()+" "+userOptional.get().getLastName(),
                    userOptional.get().getPhoneNumber(),
                    regionOptional.get(),
                    address.getDistrict(),
                    address.getStreet(),
                    address.getHome(),
                    orderOptional.get(),
                    address.getAddress()
            );
            deliveryAddressRepository.save(deliveryAddress);
            return new ApiResult(true, "Successfully added delivery Address");
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Successfully add to delivery address");
        }
    }
}
