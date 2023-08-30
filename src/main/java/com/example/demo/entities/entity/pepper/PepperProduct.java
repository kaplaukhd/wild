package com.example.demo.entities.entity.pepper;

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
    private String price;
    private String image;
    private String link;
    private boolean status;
}
