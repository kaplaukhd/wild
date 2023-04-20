package com.example.demo.service.entity.brands.impl;

import com.example.demo.entities.entity.Brands;
import com.example.demo.repository.BrandsRepository;
import com.example.demo.service.entity.brands.BrandsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandsServiceImpl implements BrandsService {

    private final BrandsRepository brandsRepository;

    @Override
    public List<Brands> findAll() {
        return brandsRepository.findAll();
    }

    @Override
    public void save(Brands brands) {
        brandsRepository.save(brands);
    }

    @Override
    public void saveAll(List<Brands> brands) {
        brandsRepository.saveAll(brands);
    }

    @Override
    public void delete(Brands brands) {
        brandsRepository.delete(brands);
    }

    @Override
    public void deleteById(Long id) {
        brandsRepository.deleteById(id);
    }
}
