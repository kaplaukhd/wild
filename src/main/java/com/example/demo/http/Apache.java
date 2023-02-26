package com.example.demo.http;

import com.example.demo.entity.xioami.Product;
import com.example.demo.entity.xioami.Root;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class Apache {

    public String json(String link) {
        String json = null;
        HttpPost httpPost = new HttpPost(link);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost.setEntity(new StringEntity("important message"));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    json = IOUtils.toString(stream, StandardCharsets.UTF_8);
                }
            }
        } catch (Exception var) {
            log.error(var.getMessage());
        }

        return json;
    }

    public List<Product> xiaomi() {
        List<Product> list = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Gson gson = new Gson();
            Root root = gson.fromJson(json(getXiaomiLink(i)), Root.class);
            list.addAll(root.getValue().getData().getModel().getProducts());
        }
        List<com.example.demo.entity.subBrands.Product> subList = subBrand();
        for (com.example.demo.entity.subBrands.Product el : subList) {
            list.add(new Product(el.getId(), el.getName(), el.getBrand(), el.getPriceU() / 100, el.getSalePriceU() / 100, el.getSale(), el.getRating(), el.getFeedbacks(), "", -1, el.getPromoTextCat()));
        }
        return list;
    }

    public List<com.example.demo.entity.subBrands.Product> subBrand() {
        List<String> list = List.of("https://catalog.wb.ru/brands/r/catalog?appType=1&brand=38501&couponsGeo=2,12,7,3,6,13,21&curr=rub&dest=-1579611&emp=0&fsupplier=-100;67466&lang=ru&locale=ru&page=1&pricemarginCoeff=1.0&reg=0&regions=80,115,64,38,4,83,33,70,69,30,86,40,1,22,66,31,48,110&sort=popular&spp=0",
                "https://catalog.wb.ru/brands/a/catalog?appType=1&brand=33663&couponsGeo=2,12,7,3,6,13,21&curr=rub&dest=-1579611&emp=0&fsupplier=-100;390287&lang=ru&locale=ru&page=1&pricemarginCoeff=1.0&reg=0&regions=80,115,64,38,4,83,33,70,69,30,86,40,1,22,66,31,48,110&sort=popular&spp=0",
                "https://catalog.wb.ru/brands/s/catalog?appType=1&brand=75762&couponsGeo=2,12,7,3,6,13,21&curr=rub&dest=-1579611&emp=0&fsupplier=-100;67466&lang=ru&locale=ru&page=1&pricemarginCoeff=1.0&reg=0&regions=80,115,64,38,4,83,33,70,69,30,86,40,1,22,66,31,48,110&sort=popular&spp=0",
                "https://catalog.wb.ru/brands/d/catalog?appType=1&brand=101894&couponsGeo=2,12,7,3,6,13,21&curr=rub&dest=-1579611&emp=0&fsupplier=-100;67466&lang=ru&locale=ru&page=1&pricemarginCoeff=1.0&reg=0&regions=80,115,64,38,4,83,33,70,69,30,86,40,1,22,66,31,48,110&sort=popular&spp=0");
        List<com.example.demo.entity.subBrands.Product> products = new ArrayList<>();
        Gson gson = new Gson();
        for (String el : list) {
            com.example.demo.entity.subBrands.Root root = gson.fromJson(json(el), com.example.demo.entity.subBrands.Root.class);
            products.addAll(root.getData().getProducts());
        }
        return products;
    }

    public String getXiaomiLink(int page) {
        return "https://www.wildberries.ru/webapi/brands/data/xiaomi/all?sort=popular&page=" + page + "&fsupplier=-100%3B67466";
    }

}
