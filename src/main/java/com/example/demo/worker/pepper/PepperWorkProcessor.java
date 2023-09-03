package com.example.demo.worker.pepper;

import com.example.demo.entities.entity.pepper.PepperProduct;
import com.example.demo.entities.enums.ProductStatus;
import com.example.demo.notify.Notification;
import com.example.demo.worker.AbstractWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class PepperWorkProcessor extends AbstractWorker<PepperProduct> {

    private final Notification notification;

    public List<PepperProduct> pepperWork(List<PepperProduct> db, List<PepperProduct> api) {

        log.info("Обновление базы данных и проверка цен [PEPPER]");
        List<PepperProduct> list = new ArrayList<>();

        List<PepperProduct> newItems = findNewItems(db, api);

        List<PepperProduct> disabledItems = findDisabledItems(db, api);

        notification(newItems);

        list.addAll(newItems);
        list.addAll(disabledItems);

        if (!list.isEmpty()) log.info("[PEPPER] OK");
        return list;
    }


    @Override
    protected boolean isActive(PepperProduct item) {
        return item.getStatus() == ProductStatus.ACTIVE;
    }

    @Override
    protected void setPositiveStatus(PepperProduct item) {
        item.setStatus(ProductStatus.ACTIVE);
    }

    @Override
    protected void setNegativeStatus(PepperProduct item) {
        item.setStatus(ProductStatus.DISABLED);
    }

    @Override
    protected boolean areEqual(PepperProduct item1, PepperProduct item2) {
        return item1.getId().equals(item2.getId());
    }

    private void notification(List<PepperProduct> objects) {
        CompletableFuture.runAsync(() -> {
            objects.forEach(product -> {
                String message = String.format("[PEPPER] %s\n%s\n%s", product.getPrice(), product.getTitle(), product.getImage());
                notification.sendMessage(message, product.getPepperLink());
            });
            log.info("[PEPPER] Sent notifications for {} items", objects.size());
        });
    }
}
