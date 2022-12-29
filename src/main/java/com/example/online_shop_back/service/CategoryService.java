package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Category;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CategoryDTO;
import com.example.online_shop_back.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResult addCategory(CategoryDTO categoryDTO) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findByName(categoryDTO.getName());

            if (categoryOptional.isPresent()) {
                return new ApiResult(false, "Category allready exists");
            }
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setIndex(categoryDTO.getIndex());
            if (categoryDTO.getCategoryId()!=null){
                category.setCategory(categoryRepository.findById(categoryDTO.getCategoryId()).orElseGet(Category::new));
            }
            categoryRepository.save(category);
            return new ApiResult(true, "Successfully added category");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult edit(UUID id, CategoryDTO categoryDTO) {
        try {
            Category category1 = categoryRepository.findById(categoryDTO.getCategoryId()).orElseGet(Category::new);
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (!categoryOptional.isPresent()) {
                return new ApiResult(false, "Category not found");
            }

            Category category = categoryOptional.get();
            category.setName(category.getName());
            category.setIndex(categoryDTO.getIndex());
            category.setCategory(category1);
            categoryRepository.save(category);
            return new ApiResult(true, "Succesfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
