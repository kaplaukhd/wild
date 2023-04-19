package com.example.demo.service.entity.impl;

import com.example.demo.entities.entity.Product;
import com.example.demo.entities.enums.ProductStatus;
import com.example.demo.http.Apache;
import com.example.demo.notify.Notification;
import com.example.demo.repository.BrandsRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.entity.ProductServiceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceEntityImpl implements ProductServiceEntity {
    private final Apache apache;
    private final ProductRepository productRepository;
    private final Notification notification;
    private final BrandsRepository brandsRepository;

    @Override
//    @Scheduled(fixedRate = 900000)
    public void updateBase() {
        log.info("Обновление базы данных и проверка цен");
        List<Product> apiData = apache.json(brandsRepository.findAll());
        log.info("Api size: " + apiData.size());
        List<Product> dbData = productRepository.findAll();
        log.info("размер базы данных = " + dbData.size());
        findNewItems(apiData, dbData);
        comparePrices(apiData, dbData);
        log.info("Обновление базы данных и проверка цен завершены\n\n\n");
    }

    private void findNewItems(List<Product> apiProducts, List<Product> dbProducts) {
        List<Product> newProducts = apiProducts
                .stream()
                .filter(apiProduct -> dbProducts
                        .stream()
                        .noneMatch(dbProduct -> apiProduct.getId().equals(dbProduct.getId())))
                .collect(Collectors.toList());
        productRepository.saveAll(newProducts);
        notification.sendProduct(newProducts);
        log.info("Добавлено новых элементов в бд: {}", newProducts.size());
    }


    private void comparePrices(List<Product> apiData, List<Product> dbData) {
        for (Product apiProduct : apiData) {
            dbData.stream()
                    .filter(dbProduct -> dbProduct.getId().equals(apiProduct.getId()))
                    .findFirst()
                    .ifPresent(dbProduct -> {
                        int dbPrice = dbProduct.getSalePriceU();
                        int apiPrice = apiProduct.getSalePriceU();
                        if (apiPrice != dbPrice) {
                            notification.message(dbProduct.getName(), apiPrice, dbPrice, apiPrice < dbPrice, dbProduct.getId());
                            productRepository.updatePrice(apiProduct.getId(), apiPrice, ProductStatus.ACTIVE);
                        }
                    });
        }
    }


    //    @Scheduled(fixedRate = 1800000)
    public void markInactiveProducts() {
        List<Product> dbProducts = productRepository.findAll();
        List<Product> apiProducts = apache.json(brandsRepository.findAll());

        for (Product dbProduct : dbProducts) {
            if (dbProduct.getStatus() == ProductStatus.ACTIVE) {
                boolean found = false;
                for (Product apiProduct : apiProducts) {
                    if (dbProduct.getId().equals(apiProduct.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    dbProduct.setStatus(ProductStatus.DISABLED);
                    productRepository.save(dbProduct);
                    notification.sendMessage(dbProduct + " \nis now inactive", "https://www.wildberries.ru/catalog/" + dbProduct.getId() + "/detail.aspx");
                }
            }
        }
    }


}
