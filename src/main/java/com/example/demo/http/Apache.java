package com.example.demo.http;

import com.example.demo.config.event.CustomEventPublisher;
import com.example.demo.entities.entity.pepper.PepperProduct;
import com.example.demo.entities.entity.product.SingleProduct;
import com.example.demo.entities.entity.search.Brands;
import com.example.demo.entities.entity.search.Product;
import com.example.demo.entities.entity.search.Root;
import com.example.demo.entities.enums.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class Apache {


    private final RestTemplate restTemplate;
    private final CustomEventPublisher eventPublisher;
    private static final String PEPPER_LINK = "https://www.pepper.ru/search?q=xiaomi&page=";

    private <K> ResponseEntity<K> get(String link, String platform, Class<K> type) {
        ResponseEntity<K> responseEntity = restTemplate.getForEntity(link, type);
        if (responseEntity.getStatusCode().isError()) {
            eventPublisher.publishCustomEvent("FAIL", String.format("[%s]", platform).toUpperCase());
        }
        return responseEntity;
    }

    public List<Product> json(List<Brands> brands) {
        return brands.stream()
                .flatMap(brand -> IntStream.rangeClosed(1, brand.getPages())
                        .mapToObj(i -> brand.getLink() + "&brand=" + brand.getId() + "&page=" + i)
                        .map(link -> Optional.ofNullable(get(link, "WB", Root.class).getBody()))
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .flatMap(response -> response.getData().getProducts().stream())
                .peek(product -> {
                    product.setPriceU(product.getPriceU() / 100);
                    product.setSalePriceU(product.getSalePriceU() / 100);
                })
                .collect(Collectors.toList());
    }

    public Optional<SingleProduct> json(Long id) {
        String link = link(id);
        ResponseEntity<SingleProduct> responseEntity = get(link, "WB (single)", SingleProduct.class);

        if (!responseEntity.getStatusCode().isError()) {
            SingleProduct singleProduct = responseEntity.getBody();
            assert singleProduct != null;
            String imageLink = link.substring(0, link.indexOf("info/ru/card.json"));
            List<String> images = new ArrayList<>(singleProduct.getMedia().photo_count);
            for (int i = 0; i < singleProduct.getMedia().photo_count; i++) {
                images.add(String.format("%simages/big/%s.jpg", imageLink, i + 1));
            }
            singleProduct.setImages(images);
            return Optional.of(singleProduct);
        } else {
            eventPublisher.publishCustomEvent("FAIL: ошибка при получении карточки", "[WILDBERRIES]");
            return Optional.empty();
        }
    }

    private String link(Long id) {
        String link = null;
        String article = String.valueOf(id);
        int length = article.length();
        String vol = "vol" + article.substring(0, (length == 7 ? 2 : (length == 8 ? 3 : 4)));
        String part = "part" + article.substring(0, (length == 7 ? 4 : (length == 8 ? 5 : 6)));
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            for (int i = 1; i <= 15; i++) {
                String variable = String.format("https://basket-%02d.wb.ru/%s/%s/%s/info/ru/card.json", i, vol, part, article);
                HttpGet request = new HttpGet(variable);
                Response response = client.execute(request, classicHttpResponse -> new Response(classicHttpResponse.getCode(), classicHttpResponse.getEntity()));
                if (response.getCode() == 200) {
                    link = variable;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't create wb bucket links");
        }

        return link;
    }

    public List<PepperProduct> pepper() {
        return IntStream.rangeClosed(1, 6)
                .mapToObj(i -> PEPPER_LINK + i)
                .map(link -> get(link, "PEPPER", String.class).getBody())
                .filter(Objects::nonNull)
                .map(this::parseHtml)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<PepperProduct> parseHtml(String stringEntity) {
        String imgClass = "thread-image";
        String titleClass = "thread-link";
        String priceClass = "thread-price";
        String statusClass = "cept-show-expired-threads";
        String pepperLink = "thread-link";
        Document doc = Jsoup.parse(stringEntity);
        Elements elements = doc.getElementsByTag("article");

        return elements.stream()
                .filter(x -> !x.id().isEmpty())
                .map(x -> {
                    Long id = Long.parseLong(x.id().substring(7));
                    String img = x.getElementsByClass(imgClass).attr("src");
                    String title = x.getElementsByClass(titleClass).attr("title");
                    String pepLink = x.getElementsByClass(pepperLink).attr("href");
                    String price = x.getElementsByClass(priceClass).text().trim().replaceAll("[^0-9]+", "");
                    String link = "https://www.pepper.ru/visit/search/" + id;
                    ProductStatus status = x.getElementsByClass(statusClass).isEmpty() ? ProductStatus.ACTIVE : ProductStatus.DISABLED;
                    return new PepperProduct(id, title, Integer.parseInt(price), img, link, status, pepLink);
                })
                .collect(Collectors.toList());
    }

}