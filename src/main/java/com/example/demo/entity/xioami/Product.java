package com.example.demo.entity.xioami;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    public Long nmId;
    public String name;
    public String brand;
    public int price;
    public int salePrice;
    public int sale;
    public int star;
    public int feedbacks;
    public String color;
    public int deliveryHours;
    public String promoTxt;

    @Override
    public String toString() {
        return "Карточка товара:\n\n " +
                "Наименование: " + name + "\n" +
                "Бренд: " + brand + "\n" +
                "Цена: " + salePrice + "\n" +
                "Промо: '" + promoTxt;
    }
}
