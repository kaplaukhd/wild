package com.example.demo.webapp.rest;

import com.example.demo.entities.entity.pepper.PepperProduct;
import com.example.demo.webapp.facade.PepperFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/api/")
@RequiredArgsConstructor
public class PepperController {


    private final PepperFacade facade;

    @GetMapping("pepper")
    public ResponseEntity<List<PepperProduct>> getAllPepper() {
        return ResponseEntity.ok(facade.getAllPepper());
    }


}
