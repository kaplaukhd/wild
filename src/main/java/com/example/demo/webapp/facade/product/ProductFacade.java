package com.example.demo.webapp.facade.product;

import com.example.demo.entities.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductFacade {

    void updateBase();

    List<ProductResponseDto> getDto();

}
