package com.example.demo.converter;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    List<E> toEntity(List<D> listDto);
}
