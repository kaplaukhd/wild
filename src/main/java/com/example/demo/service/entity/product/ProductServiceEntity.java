package com.example.demo.service.entity.product;

import com.example.demo.entities.entity.Product;

import java.util.List;

public interface ProductServiceEntity {
    void updateBase();

    void save(Product product);

    void saveAll(List<Product> productList);

    List<Product> findAll();
}
