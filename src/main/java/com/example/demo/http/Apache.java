package com.example.demo.http;

import com.example.demo.entity.xioami.Images;
import com.example.demo.entity.xioami.Product;
import com.example.demo.entity.xioami.Root;
import com.example.demo.entity.xioami.subData.Data;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.helpers.NOPLoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class Apache {
   private static final String[] basket = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};

    public String json(HttpUriRequestBase method) {
        String json = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        method.setEntity(new StringEntity("important message"));
        try (CloseableHttpResponse response = httpclient.execute(method)) {
            HttpEntity entity = response.getEntity();
            response.getCode();
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
            Root root = gson.fromJson(json(new HttpPost(getXiaomiLink(i))), Root.class);
            list.addAll(root.getValue().getData().getModel().getProducts());
        }
        List<com.example.demo.entity.subBrands.Product> subList = subBrand();
        for (com.example.demo.entity.subBrands.Product el : subList) {
            list.add(new Product(el.getId(), el.getName(), el.getBrand(), el.getPriceU() / 100, el.getSalePriceU() / 100, el.getSale(), el.getRating(), el.getFeedbacks(), "", "", -1, el.getPromoTextCat(), new HashSet<>()));
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
            com.example.demo.entity.subBrands.Root root = gson.fromJson(json(new HttpPost(el)), com.example.demo.entity.subBrands.Root.class);
            products.addAll(root.getData().getProducts());
        }
        return products;
    }

    public String getXiaomiLink(int page) {
        return "https://www.wildberries.ru/webapi/brands/data/xiaomi/all?sort=popular&page=" + page + "&fsupplier=-100%3B67466";
    }

    public List<Product> get() {
        List<Product> l = xiaomi();
        String link = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        el:
        for (Product product : l) {
            request:
           for (String s : basket) {
                String art = String.valueOf(product.getNmId());
                if (art.length() == 8) {
                    link = "https://basket-" + s + ".wb.ru/vol" + art.substring(0, 3) + "/part" + art.substring(0, 5) + "/" + art + "/info/ru/card.json";
                } else if (art.length() == 9) {
                    link = "https://basket-" + s + ".wb.ru/vol" + art.substring(0, 4) + "/part" + art.substring(0, 6) + "/" + art + "/info/ru/card.json";
                } else {
                    link = "https://basket-" + s + ".wb.ru/vol" + art.substring(0, 2) + "/part" + art.substring(0, 4) + "/" + art + "/info/ru/card.json";
                }
                HttpGet get = new HttpGet(link);
                try (CloseableHttpResponse respone = httpclient.execute(get)) {
                    if (respone.getCode() == 200) {
                        Gson gson = new Gson();
                        HttpEntity entity = respone.getEntity();
                        try (InputStream io = entity.getContent()) {
                            String js = IOUtils.toString(io, StandardCharsets.UTF_8);
                            Data data = gson.fromJson(js, Data.class);
                            product.setDescription(data.getDescription());
                        }
                    } else {
                        continue request;
                    }
                } catch (IOException var) {
                    log.error(var.getMessage());
                }
            }

        }
        return l;
    }


    public List<Product> getImg(List<Product> products) {
        String link = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        for (Product p : products) {
            String artS = String.valueOf(p.getNmId());
            int artSize = artS.length();
            basket:
            for (String s : basket) {
                Set<Images> images = new LinkedHashSet<>();
                creating:
                for (int j = 1; j < 4; j++) {
                    System.out.println(j);
                    if (artSize == 8) {
                        link = "https://basket-" + s + ".wb.ru/vol" + artS.substring(0, 3) + "/part" + artS.substring(0, 5) + "/" + artS + "/images/big/" + j + ".jpg";
                    } else if (artSize == 9) {
                        link = "https://basket-" + s + ".wb.ru/vol" + artS.substring(0, 4) + "/part" + artS.substring(0, 6) + "/" + artS + "/images/big/" + j + ".jpg";
                    } else {
                        link = "https://basket-" + s + ".wb.ru/vol" + artS.substring(0, 2) + "/part" + artS.substring(0, 4) + "/" + artS + "/images/big/" + j + ".jpg";
                    }

                    HttpGet get = new HttpGet(link);
                    try (CloseableHttpResponse respone = httpclient.execute(get)) {
                        if (respone.getCode() == 200) {
                            Images img = new Images();
                            img.setImages(link);
                            images.add(img);
                            if (j == 3) p.setImages(images);
                        } else {
                            continue basket;
                        }
                    } catch (IOException var) {
                        var.printStackTrace();
                    }

                }

            }
        }
        return products;
    }
}
