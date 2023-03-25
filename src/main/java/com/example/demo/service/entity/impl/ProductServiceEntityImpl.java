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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceEntityImpl implements ProductServiceEntity {
    private final Apache apache;
    private final ProductRepository productRepository;
    private final Notification notification;
    private final BrandsRepository brandsRepository;

    @Override
    @Scheduled(fixedRate = 900000)
    public void updateBase() {
        log.info("Обновление базы данных и проверка цен");
        // Получаем данные из API
        List<Product> apiData = apache.json(brandsRepository.findAll());
        log.info("Api size: " + apiData.size());
        // Получаем данные из базы данных
        List<Product> dbData = productRepository.findAll();
        List<Product> newItems = findNewItems(apiData, dbData);
        // Добавляем новые товары в базу данных
        addNewItems(newItems);
        // Сравниваем цены для существующих товаров
        comparePrices(apiData, dbData);
        log.info("Обновление базы данных и проверка цен завершены\n\n\n");
    }

    private List<Product> findNewItems(List<Product> apiProducts, List<Product> dbProducts) {
        List<Product> newProducts = new ArrayList<>();
        for (Product apiProduct : apiProducts) {
            boolean productExists = false;
            for (Product dbProduct : dbProducts) {
                if (apiProduct.getId().equals(dbProduct.getId())) {
                    productExists = true;
                    break;
                }
            }
            if (!productExists) {
                newProducts.add(apiProduct);
            } else {
                apiProduct.setStatus(ProductStatus.ACTIVE);
                productRepository.save(apiProduct);
            }
        }
        return newProducts;
    }

    private void addNewItems(List<Product> newItems) {
        if (!newItems.isEmpty()) {
            try {
                productRepository.saveAll(newItems);
                log.info("Добавлено новых элементов в бд: " + newItems.size());
                for (Product el : newItems) {
                    // Use the product status to determine the notification message
                    String message;
                    if (el.getStatus() == ProductStatus.DISABLED) {
                        message = "Продукт " + el.getName() + " отсутствует в API";
                    } else {
                        message = el.toString();
                    }
                    notification.sendMessage(message, "https://www.wildberries.ru/catalog/" + el.getId() + "/detail.aspx");
                }
            } catch (Exception var) {
                notification.sendMessage("Ошибка добавления новых элементов в бд", null);
                log.error("Ошибка при добавлении новых элементов в бд: " + var.getMessage());
            }
        }
    }

    private void comparePrices(List<Product> apiData, List<Product> dbData) {
        for (Product apiProduct : apiData) {
            Optional<Product> dbProductOpt = dbData.stream()
                    .filter(dbProduct -> dbProduct.getId().equals(apiProduct.getId()))
                    .findFirst();

            if (dbProductOpt.isPresent()) {
                Product dbProduct = dbProductOpt.get();
                int dbPrice = dbProduct.getSalePriceU();
                int apiPrice = apiProduct.getSalePriceU();
                if (apiPrice != dbPrice) {
                    notification.sendMessage(message(dbProduct.getName(), apiPrice, dbPrice, apiPrice < dbPrice), "https://www.wildberries.ru/catalog/" + dbProduct.getId() + "/detail.aspx");
                    updatePrice(dbProduct, apiPrice);
                }
            }
        }
    }

    private void updatePrice(Product product, int newPrice) {
        product.setSalePriceU(newPrice);
        product.setStatus(ProductStatus.ACTIVE);
        try {
            productRepository.save(product);
        } catch (Exception var) {
            notification.sendMessage("Ошибка при обновлении цены товара с id " + product.getId(), null);
            log.error("Ошибка при обновлении цены товара с id " + product.getId() + ": " + var.getMessage());
        }
    }


    @Scheduled(fixedRate = 1800000)
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
                    notification.sendMessage(dbProduct + " is now inactive", "https://www.wildberries.ru/catalog/" + dbProduct.getId() + "/detail.aspx");
                }
            }
        }
    }

    private String message(String oldProductName, int currentPrice, int oldPrice, boolean isDown) {
        String result;
        if (isDown) {
            result = "🟢";
        } else {
            result = "\uD83D\uDD34";
        }
        return "\n\nНовая цена! " + result + "\n\n" + oldProductName +
                "\n\nЦена: " + oldPrice + " --> " + currentPrice;
    }


}
