package com.example.demo.service.dto.impl;

import com.example.demo.converter.color.ColorResponseDtoMapper;
import com.example.demo.converter.product.ProductMapper;
import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final ProductMapper mapper;


    @Override
    public List<ProductResponseDto> getDto() {
        return productRepo.getProductDto();
    }

    @Override
    public Page<ProductResponseDto> getPageableDto(Pageable pageable) {
        return productRepo.findAllPageable(pageable);
    }

    @Override
    public Page<ProductResponseDto> findProduct(Pageable pageable, String name) {
        List<ProductResponseDto> dto = mapper.toDto(productRepo.findAllByNameContainingIgnoreCase(name));
        return new PageImpl<>(dto, pageable, dto.size());
    }

}
