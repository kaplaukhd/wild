package com.example.demo.entity.subBrands;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    public int __sort;
    public int ksort;
    public int time1;
    public int time2;
    public int dist;
    @Id
    public Long id;
    public int root;
    public int kindId;
    public int subjectId;
    public int subjectParentId;
    public String name;
    public String brand;
    public int brandId;
    public int siteBrandId;
    public int supplierId;
    public int sale;
    public int priceU;
    public int salePriceU;
    public int logisticsCost;
    public int saleConditions;
    public int pics;
    public int rating;
    public int feedbacks;
    public int volume;
    public boolean diffPrice;
    public int panelPromoId;
    public String promoTextCat;

    @Override
    public String toString() {
        return "Sub Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", sale=" + sale +
                ", priceU=" + priceU +
                ", salePriceU=" + salePriceU +
                ", rating=" + rating +
                ", feedbacks=" + feedbacks +
                ", promoTextCat='" + promoTextCat + '\'' +
                '}';
    }
}