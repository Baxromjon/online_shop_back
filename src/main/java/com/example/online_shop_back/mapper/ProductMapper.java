package com.example.online_shop_back.mapper;

import com.example.online_shop_back.entity.Product;
import com.example.online_shop_back.payload.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE= Mappers.getMapper(ProductMapper.class);

    ProductDTO toInfo(Product product);

//    @Mappings({
//            @Mapping(target = "name", source = "name"),
////            @Mapping(target = "category", source = "categoryId"),
////            @Mapping(target = "measurement", source = "measurementId"),
//            @Mapping(target = "price", source = "price"),
//            @Mapping(target = "standardPrice", source = "standardPrice"),
//            @Mapping(target = "discountPercent", source = "discountPercent"),
//            @Mapping(target = "description", source = "description"),
////            @Mapping(target = "photo", source = "photoId"),
//            @Mapping(target = "warrantyMonth", source = "warrantyMonth"),
////            @Mapping(target = "detail", source = "detailId"),
//            @Mapping(target = "active", source = "active"),
////            @Mapping(target = "brand", source = "brandId"),
//            @Mapping(target = "carousel", source = "carousel"),
//            @Mapping(target = "flash", source = "flash"),
//    })
//    Product getAllProduct(ProductDTO productDTO);

}
