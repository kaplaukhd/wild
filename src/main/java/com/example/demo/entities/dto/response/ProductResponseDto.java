package com.example.demo.entities.dto.response;

import com.example.demo.entities.enums.ProductStatus;
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
