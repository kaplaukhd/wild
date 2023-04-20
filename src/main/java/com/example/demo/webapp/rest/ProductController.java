package com.example.demo.webapp.rest;

import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.webapp.facade.product.ProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(productFacade.getDto(), HttpStatus.OK);
    }





}
