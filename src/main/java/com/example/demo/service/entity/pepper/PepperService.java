package com.example.demo.service.entity.pepper;

import com.example.demo.entities.entity.pepper.PepperProduct;

import java.util.List;

public interface PepperService {

    List<PepperProduct> getAll();

    void saveAll(List<PepperProduct> pepperProducts);

    void save(PepperProduct pepperProduct);

    void updateBase();

}
