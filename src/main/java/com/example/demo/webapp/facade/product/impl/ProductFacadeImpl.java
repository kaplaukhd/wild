package com.example.demo.webapp.facade.product.impl;

import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.service.dto.ProductService;
import com.example.demo.service.entity.ProductServiceEntity;
import com.example.demo.webapp.facade.product.ProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;
    private final ProductServiceEntity productServiceEntity;

    @Override
    public void updateBase() {
        productServiceEntity.updateBase();
    }

    @Override
    public void markInactiveProducts() {
            productServiceEntity.markInactiveProducts();
    }

    @Override
    public List<ProductResponseDto> getDto() {
        return productService.getDto();
    }

    @Override
    public Page<ProductResponseDto> getPageableDto(Pageable pageable) {
        return productService.getPageableDto(pageable);
    }

    @Override
    public Page<ProductResponseDto> findProduct(Pageable pageable, String name) {
        return productService.findProduct(pageable, name);
    }
}
