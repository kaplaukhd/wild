package com.example.demo.entities.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Color {

    private String name;

    @Id
    private Long id;

}
