package com.example.demo.converter.product;

import com.example.demo.converter.DtoMapper;
import com.example.demo.converter.config.ConfigMapper;
import com.example.demo.entities.dto.ProductResponseDto;
import com.example.demo.entities.entity.search.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = ConfigMapper.class)
public interface ProductMapper extends DtoMapper<ProductResponseDto, Product> {

    @Override
    @Mappings(
            {
                    @Mapping(target = "price", source = "priceU"),
                    @Mapping(target = "salePrice", source = "salePriceU"),
            }
    )
    ProductResponseDto toDto(Product product);

    @Override
    List<ProductResponseDto> toDto(List<Product> e);
}
