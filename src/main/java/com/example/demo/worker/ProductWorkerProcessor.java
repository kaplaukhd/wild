package com.example.demo.worker;

import com.example.demo.entities.entity.Product;
import com.example.demo.entities.enums.ProductStatus;
import com.example.demo.notify.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductWorkerProcessor {

    private final Notification notification;

    private static final Logger LOGGER = LogManager.getLogger(ProductWorkerProcessor.class);

    public List<Product> work(List<Product> db, List<Product> api) {
        log.info("Обновление базы данных и проверка цен");

        List<Product> newItems = api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .noneMatch(dbProduct -> apiProduct.getId().equals(dbProduct.getId())))
                .toList();


        List<Product> disabledItems = db
                .stream()
                .filter(dbProduct -> api
                        .stream()
                        .noneMatch(apiProduct -> dbProduct.getId().equals(apiProduct.getId()))
                        && dbProduct.getStatus() == ProductStatus.ACTIVE)
                .peek(dis -> dis.setStatus(ProductStatus.DISABLED))
                .toList();


        List<Product> activeItems = api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .anyMatch(dbProduct -> apiProduct.getId().equals(dbProduct.getId())
                                && dbProduct.getStatus() != ProductStatus.ACTIVE))
                .peek(active -> active.setStatus(ProductStatus.ACTIVE))
                .toList();

        List<Product> lowPriceItems = api
                .stream()
                .filter(apiProduct -> db
                        .stream()
                        .anyMatch(dbProduct -> dbProduct.getId().equals(apiProduct.getId())
                                && dbProduct.getSalePriceU() > apiProduct.getSalePriceU()))
                .toList();


        List<Product> done = new ArrayList<>(newItems);
        done.addAll(activeItems);
        done.addAll(disabledItems);
        done.addAll(lowPriceItems);

        LOGGER.info("""
                        
                        размер activeItems {}
                        размер disabledItems {}
                        размер lowPriceItems {}
                        размер newItems {}
                        Всего обновлено элементов {}

                        """,
                activeItems.size(),
                disabledItems.size(),
                lowPriceItems.size(),
                newItems.size(),
                done.size());

        CompletableFuture.runAsync(() -> notification.sendProduct(lowPriceItems));

        return done;
    }


}
