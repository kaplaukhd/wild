package com.example.demo.service.dto.impl;

import com.example.demo.converter.product.ProductMapper;
import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    @Override
    public List<ProductResponseDto> getDto() {
        return productRepo.getProductDto();
    }

}
