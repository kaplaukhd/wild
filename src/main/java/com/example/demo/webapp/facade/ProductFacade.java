package com.example.demo.webapp.facade;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.category.Category;
import com.example.demo.entities.entity.product.SingleProduct;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductFacade {

    void updateBase();

    List<ProductResponseDto> getDto();

    SingleProduct getSingleProduct(Long id);

    List<Category> getCategories();

    List<ProductResponseDto> getProductsByCategoryIds(List<Integer> ids);

    List<ProductResponseDto> getProducts(List<Integer> ids);
}
