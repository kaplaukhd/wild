package com.example.demo.entity.xioami;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
