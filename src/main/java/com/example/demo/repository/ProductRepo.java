package com.example.demo.repository;

import com.example.demo.entity.xioami.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.description = ?1 where p.nmId = ?2")
    int updateDescriptionByNmId(String description, Long nmId);
    @Transactional
    @Modifying
    @Query("update Product p set p.description = ?1 where p.description = ?2")
    void updateDescriptionByDescription(String description, String description1);
    @Transactional
    @Modifying
    @Query("update Product p set p.salePrice = ?1 where p.salePrice = ?2")
    void updateProduct(int salePrice, int salePrice1);

}
