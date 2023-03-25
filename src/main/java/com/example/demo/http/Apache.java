package com.example.demo.http;

import com.example.demo.entities.entity.Brands;
import com.example.demo.entities.entity.Product;
import com.example.demo.entities.entity.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class Apache {
    private final RestTemplate restTemplate;

    public List<Product> json(List<Brands> brands) {
        return brands.stream()
                .flatMap(brand -> IntStream.rangeClosed(1, brand.getPages())
                        .mapToObj(i -> brand.getLink() + "&brand=" + brand.getId() + "&page=" + i)
                        .map(link -> restTemplate.getForObject(link, Root.class)).filter(Objects::nonNull)
                        .map(response -> response.getData().getProducts()))
                .flatMap(Collection::stream)
                .peek(product -> {
                    product.setPriceU(product.getPriceU() / 100);
                    product.setSalePriceU(product.getSalePriceU() / 100);
                })
                .collect(Collectors.toList());
    }

}
