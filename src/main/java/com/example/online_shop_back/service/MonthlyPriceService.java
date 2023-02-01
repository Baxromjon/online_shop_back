package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Month;
import com.example.online_shop_back.entity.MonthlyPrice;
import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MonthlyPriceDTO;
import com.example.online_shop_back.projection.ProductProjection2;
import com.example.online_shop_back.repository.MonthRepository;
import com.example.online_shop_back.repository.MonthlyPriceRepository;
import com.example.online_shop_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MonthlyPriceService {
    @Autowired
    MonthRepository monthRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MonthlyPriceRepository monthlyPriceRepository;

    public ApiResult add(MonthlyPriceDTO monthDTO) {
        try {
            Optional<Month> optional = monthRepository.findById(monthDTO.getMonthId());
            if (!optional.isPresent()) {
                return new ApiResult(false, "this month not found");
            }
            Optional<Product> optionalProduct = productRepository.findById(monthDTO.getProductId());
            if (!optional.isPresent()) {
                return new ApiResult(false, "This product not found");
            }
            boolean exists = monthlyPriceRepository.existsByProductAndMonth(optionalProduct.get(), optional.get());
            if (exists){
                return new ApiResult(false, "This price allready exists");
            }
            MonthlyPrice monthlyPrice = new MonthlyPrice();
            monthlyPrice.setMonth(optional.get());
            monthlyPrice.setProduct(optionalProduct.get());
            monthlyPrice.setPrice(optionalProduct.get().getDiscountPercent() > 0 ?
                    monthDTO.getPrice() - (monthDTO.getPrice() * optionalProduct.get().getDiscountPercent() / 100) :
                    monthDTO.getPrice()
            );
            monthlyPriceRepository.save(monthlyPrice);

            return new ApiResult(true, "Successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult edit(UUID id, MonthlyPriceDTO monthDTO) {
        try {
            Optional<MonthlyPrice> monthlyPrice = monthlyPriceRepository.findById(id);
            if (!monthlyPrice.isPresent()) {
                return new ApiResult<>(false, "Monthly Price not found");
            }
            Optional<Month> optional = monthRepository.findById(monthDTO.getMonthId());
            if (!optional.isPresent()) {
                return new ApiResult(false, "this month not found");
            }
            Optional<Product> optionalProduct = productRepository.findById(monthDTO.getProductId());
            if (!optional.isPresent()) {
                return new ApiResult(false, "This product not found");
            }
            MonthlyPrice monthlyPrice1 = monthlyPrice.get();
            monthlyPrice1.setProduct(optionalProduct.get());
            monthlyPrice1.setMonth(optional.get());
            monthlyPriceRepository.save(monthlyPrice1);
            return new ApiResult(true, "Successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult getAll() {
        try {
            List<MonthlyPrice> all = monthlyPriceRepository.findAll();
            return new ApiResult(all, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Monthly Price");
        }
    }

    public ApiResult getAllProductByMonthlyPrice(UUID productId) {
        try {
            List<ProductProjection2> projection2List = monthlyPriceRepository.getAllProductByMonthPrice(productId);
            return new ApiResult(projection2List, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get Product");
        }
    }
}
