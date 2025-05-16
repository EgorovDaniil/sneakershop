package com.example.sneakershop.util;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.mapper.SneakerMapper;
import com.example.sneakershop.model.entity.Sneaker;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class SneakerMapperUtil    {

    public static List<Integer> parseSizes(String sizesStr) {
        if (sizesStr == null || sizesStr.isEmpty()) {
            return List.of();
        }
        // Убираем пробелы и разбиваем по запятой
        return Arrays.stream(sizesStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private String sizesToString(List<Integer> sizes) {
        if (sizes == null || sizes.isEmpty()) {
            return "";
        }
        return sizes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }


}
