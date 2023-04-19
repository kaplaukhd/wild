package com.example.demo.webapp.facade.product;

import com.example.demo.entities.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFacade {

    void updateBase();

    void markInactiveProducts();

    List<ProductResponseDto> getDto();

    Page<ProductResponseDto> getPageableDto(Pageable pageable);

    Page<ProductResponseDto>  findProduct(Pageable pageable, String name);

}
