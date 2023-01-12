package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Basket;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.BasketDTO;
import com.example.online_shop_back.repository.BasketRepository;
import com.example.online_shop_back.repository.ProductRepository;
import com.example.online_shop_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {
    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public ApiResult add(BasketDTO basketDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(basketDTO.getUserId());
            Optional<Product> productOptional = productRepository.findById(basketDTO.getProductId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            Optional<Basket> basketOptional = basketRepository.findByProduct(productOptional.get());
            if (basketOptional.isPresent()) {
                Basket basket1 = basketOptional.get();
                basket1.setAmount(basket1.getAmount() + 1);
                basketRepository.save(basket1);
            } else {
                Basket basket = new Basket();
                basket.setProduct(productOptional.get());
                basket.setUser(userOptional.get());
                basket.setAmount(basketDTO.getAmount());
                basketRepository.save(basket);
            }
            return new ApiResult(true, "Product successfully added to Basket");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in add product to Basket");
        }
    }

    public ApiResult edit(UUID id, BasketDTO basketDTO) {
        try {
            Optional<Basket> basketOptional = basketRepository.findById(id);
            if (!basketOptional.isPresent()) {
                return new ApiResult(false, "Basket not found");
            }
            Optional<User> userOptional = userRepository.findById(basketDTO.getUserId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            Optional<Product> productOptional = productRepository.findById(basketDTO.getProductId());
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            Basket basket = basketOptional.get();
            basket.setAmount(basketDTO.getAmount());
            basketRepository.save(basket);
            return new ApiResult(true, "Successfully edited product in Basket");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit product in Basket");
        }
    }

    public ApiResult delete(UUID id) {
        try {
            Optional<Basket> basketOptional = basketRepository.findById(id);
            if (!basketOptional.isPresent()) {
                return new ApiResult(false, "Basket not found");
            }
//            basketRepository.deleteById(id);
            basketRepository.deleteByBasketId(id);
            return new ApiResult(true, "Successfully deleted product from Basket");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in delete product from basket");
        }
    }
}
