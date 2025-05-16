package com.example.sneakershop.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public SneakerMapper sneakerMapper() {
        return Mappers.getMapper(SneakerMapper.class);
    }
}
