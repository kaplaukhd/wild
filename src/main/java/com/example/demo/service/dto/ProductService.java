package com.example.demo.service.dto;

import com.example.demo.entities.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getDto();

    void updateDto();

}
