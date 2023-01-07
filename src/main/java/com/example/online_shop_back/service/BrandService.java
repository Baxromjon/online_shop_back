package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Brand;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.BrandDTO;
import com.example.online_shop_back.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    public ApiResult add(BrandDTO brandDTO) {
        try {
            Optional<Brand> brandOptional = brandRepository.findByName(brandDTO.getName());
            if (brandOptional.isPresent()) {
                return new ApiResult(false, "This brand allready successfully");
            }
            Brand brand = new Brand(brandDTO.getName());
            brandRepository.save(brand);
            return new ApiResult(true, "Brand Successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save Brand");
        }
    }

    public ApiResult edit(UUID id, BrandDTO brandDTO) {
        try {
            Optional<Brand> brandOptional = brandRepository.findById(id);
            if (!brandOptional.isPresent()) {
                return new ApiResult(false, "Brand not found");
            }
            Brand brand = brandOptional.get();
            brand.setName(brandDTO.getName());
            brandRepository.save(brand);
            return new ApiResult(true, "Brand Successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit Brand");
        }
    }

    public ApiResult delete(UUID id) {
        try {
            Optional<Brand> brandOptional = brandRepository.findById(id);
            if (!brandOptional.isPresent()){
                return new ApiResult(false, "This Brand allready deleted");
            }
            brandRepository.deleteByBrandId(id);
            return new ApiResult(true, "Brand Successfully deleted");
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in delete Brand");
        }
    }
}
