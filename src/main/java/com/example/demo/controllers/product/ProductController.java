package com.example.demo.controllers.product;

import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.service.dto.ProductService;
import com.example.demo.service.entity.ProductServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/api/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceEntity service;
    private final ProductService serviceDto;

    @GetMapping("products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return new ResponseEntity<List<ProductResponseDto>>(serviceDto.getDto(), HttpStatus.OK);
    }


    @GetMapping("check")
    public ResponseEntity<HttpStatus> checkProducts() {
        service.updateBase();
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
