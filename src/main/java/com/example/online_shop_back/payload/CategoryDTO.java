package com.example.online_shop_back.payload;

import com.example.online_shop_back.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private String name;
    private Integer index;
    private UUID categoryId;
    private UUID photoId;
}
