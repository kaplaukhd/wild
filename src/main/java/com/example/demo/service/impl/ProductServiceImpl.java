package com.example.demo.service.impl;

import com.example.demo.entity.xioami.Product;
import com.example.demo.http.Apache;
import com.example.demo.notify.Notification;
import com.example.demo.repository.ProductRepo;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Apache apache;
    private final ProductRepo productRepo;
    private final Notification notification;

    @Override
    public List<Product> getProducts() {
        return apache.xiaomi();
    }

    @Override
//    @Scheduled(fixedRate = 900000)
    public void updateBase() {
        log.info("Обновление базы данных и проверка цен");
        // Получаем данные из API
        List<Product> apiData = getProducts();
        log.info("Api size: " + apiData.size());
        // Получаем данные из базы данных
        List<Product> dbData = productRepo.findAll();
        List<Product> newItems = findNewItems(apiData, dbData);
        // Добавляем новые товары в базу данных
        addNewItems(newItems);
        // Сравниваем цены для существующих товаров
        comparePrices(apiData, dbData);
        log.info("Обновление базы данных и проверка цен завершены\n\n\n");
    }

    private String message(String oldProductName, int currentPrice, int oldPrice, Long id, boolean isDown) {
        String result;
        if (isDown) {
            result = "🟢";
        } else {
            result = "\uD83D\uDD34";
        }
        return "\n\nНовая цена! " + result + "\n\n" + oldProductName +
                "\n\nЦена: " + oldPrice + " --> " + currentPrice;
    }

    @Override
    public void createTempFile() {
        List<Product> list = test();
        String csvFile = "C://ssh/products.csv";
        BufferedWriter writer = null;
        CSVPrinter printer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(csvFile));
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                    "Артикул",
                    "Наименование",
                    "Цена",
                    "Закупочная цена",
                    "Цвет",
                    "Описание",
                    "Картинки"
            ));
            for (Product el : list) {
                StringBuilder sb = new StringBuilder();
                if (!el.getImages().isEmpty()){
                    el.getImages().forEach(x -> {
                        if (!x.getImages().isEmpty()){
                            sb.append(x.getImages()).append(" ");
                        }
                    });
                }
                printer.printRecord(el.getNmId(), el.getName(), el.getPrice(), el.getSalePrice(), el.getColor(), el.getDescription(), sb.toString());
            }
            printer.flush();
        } catch (IOException var) {
            log.error(var.getMessage());
        } finally {
            try {
                assert writer != null;
                writer.close();
                assert printer != null;
                printer.close();
            } catch (IOException var) {
                log.error(var.getMessage());
            }

        }
    }

    @Override
    public List<Product> test() {
        List<Product> l = apache.get();
        for (Product p : l) {
            productRepo.updateDescriptionByNmId(p.getDescription(), p.getNmId());
        }
        return apache.getImg(l);
    }

    @Override
    public void download() {
    }


    private List<Product> findNewItems(List<Product> apiProducts, List<Product> dbProducts) {
        List<Product> newProducts = new ArrayList<>();

        for (Product apiProduct : apiProducts) {
            boolean productExists = false;

            for (Product dbProduct : dbProducts) {
                if (apiProduct.getNmId().equals(dbProduct.getNmId())) {
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                newProducts.add(apiProduct);
            }
        }

        return newProducts;
    }

    private void addNewItems(List<Product> newItems) {
        if (!newItems.isEmpty()) {
            try {
                productRepo.saveAll(newItems);
                log.info("Добавлено новых элементов в бд: " + newItems.size());
                for (Product el: newItems) {
                    notification.sendMessage(el.toString(), "https://www.wildberries.ru/catalog/"+ el.getNmId() + "/detail.aspx");
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
                    .filter(dbProduct -> dbProduct.getNmId().equals(apiProduct.getNmId()))
                    .findFirst();

            if (dbProductOpt.isPresent()) {
                Product dbProduct = dbProductOpt.get();
                int dbPrice = dbProduct.getSalePrice();
                int apiPrice = apiProduct.getSalePrice();
                if (apiPrice != dbPrice) {
                    notification.sendMessage(message(dbProduct.getName(), apiPrice, dbPrice, dbProduct.getNmId(), apiPrice < dbPrice), "https://www.wildberries.ru/catalog/"+ dbProduct.getNmId()+"/detail.aspx");
                    updatePrice(dbProduct, apiPrice);
                }
            }
        }
    }

    private void updatePrice(Product product, int newPrice) {
        product.setSalePrice(newPrice);
        try {
            productRepo.save(product);
        } catch (Exception var) {
            notification.sendMessage("Ошибка при обновлении цены товара с id " + product.getNmId(), null);
            log.error("Ошибка при обновлении цены товара с id " + product.getNmId() + ": " + var.getMessage());
        }
    }
}
