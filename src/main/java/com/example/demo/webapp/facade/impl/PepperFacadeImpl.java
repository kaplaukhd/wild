package com.example.demo.webapp.facade.impl;

import com.example.demo.entities.entity.pepper.PepperProduct;
import com.example.demo.service.entity.pepper.PepperService;
import com.example.demo.webapp.facade.PepperFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PepperFacadeImpl implements PepperFacade {

    private final PepperService pepperService;


    @Override
    public List<PepperProduct> getAllPepper() {
        return pepperService.getAll();
    }
}
