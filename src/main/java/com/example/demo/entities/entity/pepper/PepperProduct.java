package com.example.demo.entities.entity.pepper;

import com.example.demo.entities.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PepperProduct {
    @Id
    private Long id;
    private String title;
    private int price;
    private String image;
    private String link;
    private ProductStatus status;
    private String pepperLink;
}
