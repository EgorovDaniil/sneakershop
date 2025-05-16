package com.example.sneakershop.mapper;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.util.SneakerMapperUtil;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SneakerMapper {
    SneakerDTO sneakerToSneakerDTO(Sneaker sneaker);

    Sneaker sneakerDTOToSneaker(SneakerDTO sneakerDTO);


    @AfterMapping
    default void mapSizes(Sneaker sneaker, @MappingTarget SneakerDTO dto) {
        dto.setSizes(SneakerMapperUtil.parseSizes(sneaker.getSize()));
    }


}
