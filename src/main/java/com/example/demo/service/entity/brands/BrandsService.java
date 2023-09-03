package com.example.demo.service.entity.brands;

import com.example.demo.entities.entity.search.Brands;

import java.util.List;

public interface BrandsService {

    List<Brands> findAll();

    void save(Brands brands);

    void saveAll(List<Brands> brands);

    void delete(Brands brands);

    void deleteById(Long id);
}
