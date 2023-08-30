package com.example.demo.service.entity.product.impl;

import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.entities.entity.search.Product;
import com.example.demo.http.Apache;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.entity.product.ProductServiceEntity;
import com.example.demo.service.entity.brands.BrandsService;
import com.example.demo.worker.ProductWorkerProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceEntityImpl implements ProductServiceEntity {

    private final Apache apache;
    private final ProductRepository productRepository;
    private final BrandsService brandsService;
    private final ProductWorkerProcessor processor;

    @Override
    @Scheduled(fixedRate = 900000)
    public void updateBase() {
        List<Product> updatedList = processor.work(findAll(), apache.json(brandsService.findAll()));
        saveAll(updatedList);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void saveAll(List<Product> productsList) {
        productRepository.saveAll(productsList);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public SingleProduct getSingleProduct(Long id) {
        return apache.json(id);
    }
}
