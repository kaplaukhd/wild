package com.example.demo.converter.color;

import com.example.demo.converter.DtoMapper;
import com.example.demo.converter.config.ConfigMapper;
import com.example.demo.entities.dto.response.ColorResponseDto;
import com.example.demo.entities.entity.Color;
import org.mapstruct.Mapper;

@Mapper(config = ConfigMapper.class)
public interface ColorResponseDtoMapper extends DtoMapper<ColorResponseDto, Color> {
}
