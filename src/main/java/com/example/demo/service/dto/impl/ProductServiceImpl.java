package com.example.demo.service.dto.impl;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    @Override
    @Cacheable("dto")
    public List<ProductResponseDto> getDto() {
        return productRepo.getProductDto();
    }

    @Override
    @CacheEvict(value = "dto", allEntries = true)
    @Scheduled(cron = "0 0 */3 * * *")
    public void updateDto() {
        productRepo.getProductDto();
    }


}
