package com.example.demo.entity.dto;

import com.example.demo.entity.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDto {

    private Long nmId;
    private String name;
    private String brand;
    private int price;
    private int salePrice;
    private ProductStatus status;
}
