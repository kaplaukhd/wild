package com.example.demo.converter.product;

import com.example.demo.converter.DtoMapper;
import com.example.demo.converter.color.ColorResponseDtoMapper;
import com.example.demo.converter.config.ConfigMapper;
import com.example.demo.entities.dto.response.ProductResponseDto;
import com.example.demo.entities.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = ConfigMapper.class, uses = {ColorResponseDtoMapper.class})
public interface ProductResponseDtoMapper extends DtoMapper<ProductResponseDto, Product> {

    @Override
    @Mapping(source = "id", target = "nmId")
    @Mapping(source = "salePriceU", target = "salePrice")
    @Mapping(source = "priceU", target = "price")
    ProductResponseDto toDto(Product product);

    @Override
    List<ProductResponseDto> toDto(List<Product> e);
}
