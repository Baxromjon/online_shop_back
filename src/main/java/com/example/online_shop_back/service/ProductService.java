package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.*;
import com.example.online_shop_back.mapper.ProductMapper;
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
            List<Attachment> attachmentList = attachmentRepository.findAllById(productDTO.getPhotoId());
            List<Detail> detailList = detailRepository.findAllById(productDTO.getDetailId());
            if (!categoryOptional.isPresent()) {
                return new ApiResult(false, "Category not found");
            }
            if (!measurementOptional.isPresent()) {
                return new ApiResult(false, "measurement not found");
            }
            if (detailList.isEmpty()) {
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
            product.setWarrantyMonth(productDTO.getWarrantyMonth());
            product.setDetail(detailList);
            product.setPhoto(attachmentList);
            product.setActive(true);
            product.setBrand(brandOptional.get());
            product.setFlash(productDTO.isFlash());
            product.setCarousel(productDTO.isCarousel());
            product.setTotalPrice(productDTO.getDiscountPercent() > 0 ?
                    productDTO.getPrice() - (productDTO.getPrice() * productDTO.getDiscountPercent() / 100) : productDTO.getPrice()
            );
            product.setMainPhoto(attachmentList.get(0));

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
            List<Detail> detailList = detailRepository.findAllById(productDTO.getDetailId());
            if (!categoryOptional.isPresent()) {
                return new ApiResult(false, "Category not found");
            }
            if (!measurementOptional.isPresent()) {
                return new ApiResult(false, "measurement not found");
            }
            if (detailList.isEmpty()) {
                return new ApiResult<>(false, "Detail not found");
            }
            Optional<Brand> brandOptional = brandRepository.findById(productDTO.getBrandId());
            if (!brandOptional.isPresent()) {
                return new ApiResult(false, "Brand not found");
            }
            Product product = productOptional.get();
            product.setCategory(categoryOptional.get());
            product.setDetail(detailList);
            product.setName(productDTO.getName());
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get Product by Id");
        }
    }

    public ApiResult getAll() {
        try {
            List<Product> all = productRepository.findAll();
            return new ApiResult(all, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error");
        }
    }

    public ApiResult getAllByCategory(UUID id) {
        try {
            List<Product> allByCategory = productRepository.getAllByCategory(id);
            return new ApiResult(allByCategory, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Products by Category");
        }
    }

    public ApiResult getAllByFlash() {
        try {
            List<Product> allByFlashIsTrue = productRepository.getAllByFlashIsTrue();
            return new ApiResult(allByFlashIsTrue, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Products by Flash");
        }
    }

    public ApiResult getAllByCarousel() {
        try {
            List<Product> allByFlashIsTrue = productRepository.getAllByCarouselIsTrue();
            return new ApiResult(allByFlashIsTrue, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Products by Carousel");
        }
    }

    public ApiResult editMainPhoto(UUID productId, UUID photoId) {
        try {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (!productOptional.isPresent()) {
                return new ApiResult(false, "Product not found");
            }
            Optional<Attachment> attachmentOptional = attachmentRepository.findById(photoId);
            if (!attachmentOptional.isPresent()) {
                return new ApiResult(false, "Photo not found");
            }
            Product product = productOptional.get();
            product.setMainPhoto(attachmentOptional.get());
            productRepository.save(product);
            return new ApiResult(true, "Successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error");
        }
    }
}
