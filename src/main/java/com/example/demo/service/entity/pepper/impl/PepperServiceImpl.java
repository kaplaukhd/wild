package com.example.demo.service.entity.pepper.impl;

import com.example.demo.entities.entity.pepper.PepperProduct;
import com.example.demo.http.Apache;
import com.example.demo.repository.PepperRepository;
import com.example.demo.service.entity.pepper.PepperService;
import com.example.demo.worker.ProductWorkerProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PepperServiceImpl  implements PepperService {

    private final PepperRepository pepperRepository;
    private final Apache apache;
    private final ProductWorkerProcessor processor;


    @Override
    public List<PepperProduct> getAll() {
        return pepperRepository.findAll();
    }


    @Override
    public void saveAll(List<PepperProduct> pepperProducts) {
        pepperRepository.saveAll(pepperProducts);
    }


    @Override
    public void save(PepperProduct pepperProduct) {
        pepperRepository.save(pepperProduct);
    }


    @Override
    @Scheduled(fixedRate = 900000)
    public void updateBase() {
        List<PepperProduct> updatedList = processor.pepperWork(pepperRepository.findAll(), apache.pepper());
        pepperRepository.saveAll(updatedList);
    }


}
