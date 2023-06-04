package com.example.demo.webapp.facade.impl;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.service.dto.ProductService;
import com.example.demo.service.entity.product.ProductServiceEntity;
import com.example.demo.webapp.facade.ProductFacade;
import lombok.RequiredArgsConstructor;
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
    public List<ProductResponseDto> getDto() {
        return productService.getDto();
    }

    @Override
    public SingleProduct getSingleProduct(Long id) {
        return productServiceEntity.getSingleProduct(id);
    }


}
