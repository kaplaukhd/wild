package com.example.demo.converter;


import java.util.List;

public interface DtoMapper<D, E> {
    D toDto(E e);
    List<D> toDto(List<E> e);
}
