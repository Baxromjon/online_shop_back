package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Attachment;
import com.example.online_shop_back.entity.Brand;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.BrandDTO;
import com.example.online_shop_back.repository.AttachmentRepository;
import com.example.online_shop_back.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public ApiResult add(BrandDTO brandDTO) {
        try {
            Optional<Brand> brandOptional = brandRepository.findByName(brandDTO.getName());
            Optional<Attachment> attachmentOptional = attachmentRepository.findById(brandDTO.getPhotoId());
            if (brandOptional.isPresent()) {
                return new ApiResult(false, "This brand allready successfully");
            }
            if (!attachmentOptional.isPresent()){
                return new ApiResult(false, "Photo not found");
            }
            Brand brand = new Brand(brandDTO.getName(), attachmentOptional.get());
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
            Optional<Attachment> attachmentOptional = attachmentRepository.findById(brandDTO.getPhotoId());
            if (!brandOptional.isPresent()) {
                return new ApiResult(false, "Brand not found");
            }
            if (!attachmentOptional.isPresent()){
                return new ApiResult(false, "Photo not found");
            }
            Brand brand = brandOptional.get();
            brand.setName(brandDTO.getName());
            brand.setAttachment(attachmentOptional.get());
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

    public ApiResult getAll() {
        try {
            List<Brand> all = brandRepository.findAll();
            return new ApiResult(all, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get all brands");
        }
    }
}
