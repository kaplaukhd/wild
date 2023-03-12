package com.example.demo.entity.xioami;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private Long nmId;
    private String name;
    private String brand;
    private int price;
    private int salePrice;
    private int sale;
    private int star;
    private int feedbacks;
    private String color;
    private String description = " ";
    private int deliveryHours;
    private String promoTxt;
    @OneToMany(mappedBy = "images")
    private Set<Images> images = new LinkedHashSet<>();



    @Override
    public String toString() {
        return "Карточка товара:\n\n" +
                "Наименование: " + name + "\n" +
                "Бренд: " + brand + "\n" +
                "Цена: " + salePrice + "\n" +
                "Промо: '" + promoTxt;
    }
}
