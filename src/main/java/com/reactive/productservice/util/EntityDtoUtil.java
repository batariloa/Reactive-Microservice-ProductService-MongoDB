package com.reactive.productservice.util;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.entity.Product;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product){

        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getPrice());
    }

    public static Product toEntity(ProductDto productDto){

        return new Product(
                productDto.getId(),
                productDto.getDescription(),
                productDto.getPrice());
    }
}
