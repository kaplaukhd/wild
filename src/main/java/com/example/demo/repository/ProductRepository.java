package com.example.demo.repository;

import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.entities.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.example.demo.entities.dto.response.ProductResponseDto(p.id, p.name, p.brand, p.priceU, p.salePriceU, p.status) FROM Product p")
    List<ProductResponseDto> getProductDto();

}
