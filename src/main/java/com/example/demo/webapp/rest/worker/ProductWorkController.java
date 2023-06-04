package com.example.demo.webapp.rest.worker;

import com.example.demo.webapp.facade.ProductFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/work/")
@RequiredArgsConstructor
public class ProductWorkController {


    private final ProductFacade productFacade;

    @GetMapping("check")
    public ResponseEntity<HttpStatus> checkProducts() {
        productFacade.updateBase();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
