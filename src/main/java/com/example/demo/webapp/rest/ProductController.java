package com.example.demo.webapp.rest;

import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.webapp.facade.ProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/api/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping("products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return ResponseEntity.ok(productFacade.getDto());
    }

    @GetMapping("products/{id}")
    public ResponseEntity<SingleProduct> getProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productFacade.getSingleProduct(id));
    }
}
