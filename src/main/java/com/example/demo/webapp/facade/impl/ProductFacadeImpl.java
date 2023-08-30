package com.example.demo.webapp.facade.impl;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.category.Category;
import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.service.dto.ProductService;
import com.example.demo.service.entity.category.CategoryService;
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
    private final CategoryService categoryService;

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

    @Override
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @Override
    public List<ProductResponseDto> getProductsByCategoryIds(List<Integer> ids) {
        return productService.getProductsByCategoriesId(ids);
    }

    @Override
    public List<ProductResponseDto> getProducts(List<Integer> ids) {
        if (ids == null) {
            return productService.getDto();
        }
        return productService.getProductsByCategoriesId(ids);
    }


}
