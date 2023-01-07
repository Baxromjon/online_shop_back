package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.entity.WishList;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.WishListDTO;
import com.example.online_shop_back.repository.ProductRepository;
import com.example.online_shop_back.repository.UserRepository;
import com.example.online_shop_back.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public ApiResult add(WishListDTO wishListDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(wishListDTO.getUserId());
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            Optional<Product> productOptional = productRepository.findById(wishListDTO.getProductId());
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            WishList wishList = new WishList(userOptional.get(), productOptional.get());
            wishListRepository.save(wishList);
            return new ApiResult(true, "Successfully saved");
        } catch (Exception e) {
            return new ApiResult(false, "Error in save");
        }
    }

    public ApiResult delete(UUID id) {
        try {
            Optional<WishList> wishListOptional = wishListRepository.findById(id);
            if (!wishListOptional.isPresent()) {
                return new ApiResult(false, "Wish List not found");
            }
//            wishListRepository.deleteById(id);
            wishListRepository.deleteWishListById(id);
            return new ApiResult(true, "Successfully deleted");
        } catch (Exception e) {
            return new ApiResult(false, "Error in delete wishlist");
        }
    }

    public ApiResult getAllByUserId(UUID id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                return new ApiResult(false, "User not found");
            }
            List<WishList> allByUser = wishListRepository.findAllByUser(userOptional.get());
            return new ApiResult(allByUser, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Wish List by User");
        }
    }
}
