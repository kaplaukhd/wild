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

    @GetMapping("products/page/{page}")
    public Page<ProductResponseDto> getSortedProducts(@PathVariable int page,
                                                      @RequestParam(defaultValue = "50",
                                                                    required = false) int countItems) {
        return productFacade.getPageableDto(PageRequest.of(page, countItems));
    }

    @GetMapping("products/page/{page}/find")
    public Page<ProductResponseDto> findProduct(@PathVariable int page,
                                                      @RequestParam(defaultValue = "50",
                                                              required = false) int countItems,
                                                @RequestParam(required = false) String name) {
        return productFacade.findProduct(PageRequest.of(page -1, countItems), name);
    }


    @GetMapping("check")
    public ResponseEntity<HttpStatus> checkProducts() {
        productFacade.updateBase();
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
