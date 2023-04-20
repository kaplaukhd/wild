package com.example.demo.service.dto;

import com.example.demo.entities.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getDto();

}
