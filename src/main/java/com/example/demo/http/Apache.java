package com.example.demo.http;

import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.entities.entity.search.Brands;
import com.example.demo.entities.entity.search.Product;
import com.example.demo.entities.entity.search.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    public SingleProduct getJson(Long id) {
        String link = link(id);
        SingleProduct singleProduct = restTemplate.getForObject(link, SingleProduct.class);
        if (singleProduct != null) {
            String imageLink = link.substring(0, link.indexOf("info/ru/card.json"));
            List<String> images = new ArrayList<>(singleProduct.getMedia().photo_count);
            for (int i = 0; i < singleProduct.getMedia().photo_count; i++) {
                images.add(String.format("%simages/big/%s.jpg", imageLink, i + 1));
            }
            singleProduct.setImages(images);
        }
        return singleProduct;
    }

    public String link(Long id) {
        String link = null;
        String article = String.valueOf(id);
        int length = article.length();
        String vol = "vol" + article.substring(0, (length == 7 ? 2 : (length == 8 ? 3 : 4)));
        String part = "part" + article.substring(0, (length == 7 ? 4 : (length == 8 ? 5 : 6)));
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            for (int i = 1; i <= 10; i++) {
                String variable = String.format("https://basket-%02d.wb.ru/%s/%s/%s/info/ru/card.json", i, vol, part, article);
                HttpGet request = new HttpGet(variable);
                try (CloseableHttpResponse response = client.execute(request)) {
                    if (response.getCode() == 200) {
                        link = variable;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return link;
    }
}
