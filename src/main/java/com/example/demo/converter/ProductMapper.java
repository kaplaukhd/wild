package com.example.demo.converter;

import com.example.demo.entity.dto.ProductResponseDto;
import com.example.demo.entity.xioami.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toDto(Product product);

    List<ProductResponseDto> toListDto(List<Product> products);
}
