package com.example.demo.worker.wild;

import com.example.demo.entities.enums.ProductPriceStatus;
import com.example.demo.entities.enums.ProductStatus;
import com.example.demo.entities.entity.search.Product;
import com.example.demo.notify.Notification;
import com.example.demo.worker.AbstractWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class WildWorkProcessor extends AbstractWorker<Product> {

    private final Notification notification;

    public List<Product> work(List<Product> db, List<Product> api) {

        log.info("Обновление базы данных и проверка цен [WILDBERRIES]");

        List<Product> newItems = findNewItems(db, api);
        List<Product> disabledItems = findDisabledItems(db, api);
        List<Product> activeItems = findActiveItems(api, db);
        List<Product> lowPriceItems = findLowPriceItems(api, db);
        List<Product> highPriceItems = findHighPriceItems(api, db);

        List<Product> done = new ArrayList<>(newItems);
        done.addAll(activeItems);
        done.addAll(disabledItems);
        done.addAll(lowPriceItems);
        done.addAll(highPriceItems);

        Map<Boolean, List<Product>> notify = new HashMap<>();

        notify.put(true, highPriceItems);
        notify.put(false, lowPriceItems);

        sendNotification(notify);


        if (!done.isEmpty()) log.info("[WB] OK");
        return done;
    }

    @Override
    protected boolean isActive(Product item) {
        return item.getStatus() == ProductStatus.ACTIVE;
    }

    @Override
    protected void setNegativeStatus(Product item) {
        item.setStatus(ProductStatus.DISABLED);
    }

    @Override
    protected void setPositiveStatus(Product item) {
        item.setStatus(ProductStatus.ACTIVE);
    }

    @Override
    protected boolean areEqual(Product item1, Product item2) {
        return item1.getId().equals(item2.getId());
    }

    private List<Product> findActiveItems(List<Product> api, List<Product> db) {
        return api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .anyMatch(dbProduct -> areEqual(apiProduct, dbProduct)
                                && !isActive(dbProduct)))
                .peek(this::setPositiveStatus)
                .toList();
    }

    private List<Product> findLowPriceItems(List<Product> api, List<Product> db) {
        return api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .anyMatch(dbProduct -> areEqual(dbProduct, apiProduct)
                                && dbProduct.getSalePriceU() > apiProduct.getSalePriceU()))
                .peek(apiProduct -> {
                    Product dbProduct = db.stream()
                            .filter(p -> p.getId().equals(apiProduct.getId()))
                            .findFirst()
                            .orElseThrow(); // или можно обработать исключение в случае отсутствия товара в БД
                    apiProduct.setPriceStatus(ProductPriceStatus.LOW_PRICE);
                    apiProduct.setOldPrice(dbProduct.getSalePriceU());
                })
                .toList();
    }

    private List<Product> findHighPriceItems(List<Product> api, List<Product> db) {
        return api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .anyMatch(dbProduct -> areEqual(dbProduct, apiProduct)
                                && dbProduct.getSalePriceU() < apiProduct.getSalePriceU()))
                .peek(apiProduct -> {
                    Product dbProduct = db.stream()
                            .filter(p -> p.getId().equals(apiProduct.getId()))
                            .findFirst()
                            .orElseThrow();
                    apiProduct.setPriceStatus(ProductPriceStatus.HIGH_PRICE);
                    apiProduct.setOldPrice(dbProduct.getSalePriceU());
                })
                .toList();
    }

    private void sendNotification(Map<Boolean, List<Product>> objects) {
        CompletableFuture.runAsync(() -> notification.sendProduct(objects));

    }
}