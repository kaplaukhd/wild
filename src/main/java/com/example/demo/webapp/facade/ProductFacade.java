package com.example.demo.webapp.facade;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.product.SingleProduct;

import java.util.List;

public interface ProductFacade {

    void updateBase();

    List<ProductResponseDto> getDto();

    SingleProduct getSingleProduct(Long id);

}
