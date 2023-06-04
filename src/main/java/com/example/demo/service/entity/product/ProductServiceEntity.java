package com.example.demo.service.entity.product;

import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.entities.entity.search.Product;

import java.util.List;

public interface ProductServiceEntity {
    void updateBase();

    void save(Product product);

    void saveAll(List<Product> productList);

    List<Product> findAll();

    SingleProduct getSingleProduct(Long id);
}
