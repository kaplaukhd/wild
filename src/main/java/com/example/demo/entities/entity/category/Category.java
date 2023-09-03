package com.example.demo.entities.entity.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "categories")
public class Category {
    @Id
    private Long id;
    private String name;
    private int count;
}
