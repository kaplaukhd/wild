package com.example.demo.repository;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.search.Product;
import com.example.demo.entities.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.example.demo.entities.dto.ProductResponseDto(p.id, " +
            "p.name," +
            " p.brand," +
            " p.priceU, " +
            "p.salePriceU," +
            " p.status," +
            " p.priceStatus) FROM Product p order by p.status ASC")
    List<ProductResponseDto> getProductDto();


    @Query("SELECT new com.example.demo.entities.dto.ProductResponseDto(p.id, " +
            "p.name," +
            " p.brand, " +
            "p.priceU," +
            " p.salePriceU," +
            " p.status," +
            " p.priceStatus) FROM Product p")
    Page<ProductResponseDto> findAllPageable(Pageable pageable);


    List<Product> findAllByNameContainingIgnoreCase(String name);

    @Modifying
    @Query("UPDATE Product SET salePriceU = :newPrice, status = :status WHERE id = :productId")
    void updatePrice(@Param(value = "productId") Long id,
                     @Param(value = "newPrice") int newPrice,
                     @Param(value = "status") ProductStatus status);


    @Modifying
    @Query("update Product set status = :status where id = :id")
    void changeStatus(@Param(value = "status") ProductStatus status,
                      @Param(value = "id") Long id);
}
