package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MonthlyPriceDTO;
import com.example.online_shop_back.payload.ProductDTO;
import com.example.online_shop_back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MonthlyPriceRepository monthlyPriceRepository;

    @Autowired
    DetailRepository detailRepository;

    @Autowired
    BrandRepository brandRepository;

    public ApiResult add(ProductDTO productDTO) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
            Optional<Measurement> measurementOptional = measurementRepository.findById(productDTO.getMeasurementId());
//            List<Attachment> attachment = null;
//            for (int i = 0; i < productDTO.getPhotoId().size(); i++) {
//                attachment = attachmentRepository.findById(productDTO.getPhotoId().get(i)).orElseGet(Attachment::new);
                List<Attachment> attachmentList = attachmentRepository.findAllById(productDTO.getPhotoId());
//            }
//        Optional<MonthlyPrice> monthlyPriceOptional = monthlyPriceRepository.findById(productDTO.getMonthlyPriceId());
            Optional<Detail> detailOptional = detailRepository.findById(productDTO.getDetailId());
            if (!categoryOptional.isPresent()) {
                return new ApiResult(false, "Category not found");
            }
            if (!measurementOptional.isPresent()) {
                return new ApiResult(false, "measurement not found");
            }
//            if (!monthlyPriceOptional.isPresent()) {
//                return new ApiResult(false, "Monthly price not found");
//            }
            if (!detailOptional.isPresent()) {
                return new ApiResult<>(false, "Detail not found");
            }
            Optional<Product> optional = productRepository.findByNameAndCategoryAndMeasurement(productDTO.getName(), categoryOptional.get(), measurementOptional.get());
            if (optional.isPresent()) {
                return new ApiResult(false, "This Product allready exists");
            }

            Optional<Brand> brandOptional = brandRepository.findById(productDTO.getBrandId());
            if (!brandOptional.isPresent()) {
                return new ApiResult(false, "Brand not found");
            }
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setCategory(categoryOptional.get());
            product.setMeasurement(measurementOptional.get());
            product.setPrice(productDTO.getPrice());
            product.setDiscountPercent(productDTO.getDiscountPercent());
            product.setDescription(productDTO.getDescription());
//                    attachment,
            product.setWarrantyMonth(productDTO.getWarrantyMonth());
            product.setDetail(detailOptional.get());
            product.setPhoto(attachmentList);
            product.setActive(productDTO.isActive());
            product.setBrand(brandOptional.get());
            product.setFlash(productDTO.isFlash());
            product.setCarousel(productDTO.isCarousel());

            productRepository.save(product);
            return new ApiResult(true, "Product successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult<>(false, "Error in save product!");
        }
    }

    public ApiResult edit(UUID id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
            Optional<Measurement> measurementOptional = measurementRepository.findById(productDTO.getMeasurementId());
//            Attachment attachment = attachmentRepository.findById(productDTO.getPhotoId()).orElseGet(Attachment::new);
//            Optional<MonthlyPrice> monthlyPriceOptional = monthlyPriceRepository.findById(productDTO.getMonthlyPriceId());
            Optional<Detail> detailOptional = detailRepository.findById(productDTO.getDetailId());
            if (!categoryOptional.isPresent()) {
                return new ApiResult(false, "Category not found");
            }
            if (!measurementOptional.isPresent()) {
                return new ApiResult(false, "measurement not found");
            }
//            if (!monthlyPriceOptional.isPresent()) {
//                return new ApiResult(false, "Monthly price not found");
//            }
            if (!detailOptional.isPresent()) {
                return new ApiResult<>(false, "Detail not found");
            }
            Optional<Brand> brandOptional = brandRepository.findById(productDTO.getBrandId());
            if (!brandOptional.isPresent()) {
                return new ApiResult(false, "Brand not found");
            }
            Product product = productOptional.get();
            product.setCategory(categoryOptional.get());
            product.setDetail(detailOptional.get());
            product.setName(product.getName());
//            product.setPhoto(Collections.singletonList(attachment));
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setMeasurement(measurementOptional.get());
            product.setDiscountPercent(productDTO.getDiscountPercent());
            product.setWarrantyMonth(productDTO.getWarrantyMonth());
            product.setActive(productDTO.isActive());
            product.setCarousel(productDTO.isCarousel());
            product.setFlash(productDTO.isFlash());
            product.setBrand(brandOptional.get());

            productRepository.save(product);
            return new ApiResult(true, "Successfully edited");
        }
        return new ApiResult(false, "Product not found");
    }

    public ApiResult getById(UUID id) {
        try {
            Product product = productRepository.findById(id).orElseThrow();
            return new ApiResult(product, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get Product by Id");
        }
    }

    public ApiResult getAll() {
        try {
            List<Product> all = productRepository.findAll();
            return new ApiResult(all, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error");
        }
    }
}
