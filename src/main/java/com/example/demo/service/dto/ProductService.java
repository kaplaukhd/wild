package com.example.demo.service.dto;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.search.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getDto();

    void updateDto();

    List<ProductResponseDto> getProductsByCategoriesId(List<Integer> categories);

}
