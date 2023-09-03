package com.example.demo.entities.entity.search;

import com.example.demo.entities.enums.ProductPriceStatus;
import com.example.demo.entities.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product   {
    private int __sort;
    private int ksort;
    private int time1;
    @Column(name = "deliveryHours")
    private int time2;
    private int dist;
    @Id
    @Column(name = "nm_id")
    private Long id;
    private int root;
    private int kindId;
    private int subjectId;
    private int subjectParentId;
    private String name;
    private String brand;
    private int brandId;
    private int siteBrandId;
    private int supplierId;

    private int sale;
    @Column(name = "price")
    private int priceU;
    @Column(name = "sale_price")
    private int salePriceU;
    @Column(name = "old_sale_price")
    private Integer oldPrice;
    private int logisticsCost;
    private int saleConditions;
    private int pics;
    @Column(name = "star")
    private int rating;
    private int feedbacks;
    private int volume;
    private boolean diffPrice;
    private int panelPromoId;
    @Column(name = "promoTxt")
    private String promoTextCat;
    @Column(name = "status")
    private ProductStatus status = ProductStatus.ACTIVE;
    @Column(name = "price_status")
    private ProductPriceStatus priceStatus = ProductPriceStatus.DEFAULT_PRICE;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Images> images = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Карточка товара:\n\n" +
                "Наименование: " + name + "\n" +
                "Бренд: " + brand + "\n" +
                "Цена: " + salePriceU + "\n" +
                "Промо: '" + promoTextCat;
    }
}