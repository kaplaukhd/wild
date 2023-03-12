package com.example.demo.controllers;

import com.example.demo.entity.xioami.Product;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {
    private final ProductServiceImpl service;

    @Autowired
    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<List<Product>>(service.getProducts(), HttpStatus.OK);
    }


    @GetMapping("check")
    public ResponseEntity<HttpStatus> checkProducts() {
        service.updateBase();
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("temp")
    public ResponseEntity<HttpStatus> temp() {
        service.createTempFile();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity<List<Product>> test() {
        return ResponseEntity.ok(service.test());
    }

}
