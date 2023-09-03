package com.example.demo.entities.entity.search;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String images;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
