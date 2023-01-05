package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Criteria;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.entity.Rate;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RateDTO;
import com.example.online_shop_back.repository.CriteriaRepository;
import com.example.online_shop_back.repository.ProductRepository;
import com.example.online_shop_back.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RateService {
    @Autowired
    RateRepository rateRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CriteriaRepository criteriaRepository;


    public ApiResult add(RateDTO rateDTO) {
        try {
            Optional<Product> productOptional = productRepository.findById(rateDTO.getProductId());
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            Optional<Criteria> criteriaOptional = criteriaRepository.findById(rateDTO.getCriteriaId());
            if (!criteriaOptional.isPresent()) {
                return new ApiResult(false, "Criteria not found");
            }
            Rate rate = new Rate(rateDTO.getStarCount(), productOptional.get(), criteriaOptional.get());
            rateRepository.save(rate);
            return new ApiResult(true, "Successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save criteria");
        }
    }

    public ApiResult edit(UUID id, RateDTO rateDTO) {
        try {
            Optional<Rate> rateOptional = rateRepository.findById(id);
            if (!rateOptional.isPresent()) {
                return new ApiResult(false, "Rate not found");
            }
            Optional<Product> productOptional = productRepository.findById(rateDTO.getProductId());
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            Optional<Criteria> criteriaOptional = criteriaRepository.findById(rateDTO.getCriteriaId());
            if (!criteriaOptional.isPresent()) {
                return new ApiResult(false, "Criteria not found");
            }
            Rate rate = rateOptional.get();
            rate.setCriteria(criteriaOptional.get());
            rate.setProduct(productOptional.get());
            rate.setStarCount(rateDTO.getStarCount());
            rateRepository.save(rate);
            return new ApiResult(true, "Rate successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit Rate");
        }
    }
}
