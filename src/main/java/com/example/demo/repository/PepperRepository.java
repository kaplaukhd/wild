package com.example.demo.repository;

import com.example.demo.entities.entity.pepper.PepperProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PepperRepository extends JpaRepository<PepperProduct, Long> {

}
