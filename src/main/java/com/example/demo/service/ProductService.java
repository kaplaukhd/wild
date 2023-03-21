package com.example.demo.service;

import com.example.demo.entity.dto.ProductResponseDto;
import com.example.demo.entity.xioami.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    void updateBase();

    void createTempFile() throws IOException;

    List<Product>  test();

    void download();

    List<ProductResponseDto> getDto();
}
