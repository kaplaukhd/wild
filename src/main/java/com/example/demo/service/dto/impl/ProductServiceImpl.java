package com.example.demo.service.dto.impl;

import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    @Override
    public List<ProductResponseDto> getDto() {
        //TODO
        return productRepo.getProductDto();
    }

}
