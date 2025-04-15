package com.example.sneakershop.dto;

import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.enums.SneakerCategory;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SneakerDTO {

    private Long id;
    private String name;
    private String brand;
    private Double price;
    @ElementCollection
    private List<Integer> sizes; // Например: [38, 39, 40, 42]
    private String imageUrl;
    private SneakerCategory category;
    public SneakerDTO() {
    }

    public SneakerDTO(Sneaker sneaker) {
        this.id = sneaker.getId();
        this.name = sneaker.getName();
        this.brand = sneaker.getBrand();
        this.price = sneaker.getPrice();
        this.sizes = parseSizes(sneaker.getSize());
        this.imageUrl = sneaker.getImageUrl();
        this.category = sneaker.getCategory();
    }

    // Вынесенная логика парсинга
    private static List<Integer> parseSizes(String sizesString) {
        if (sizesString == null || sizesString.isBlank()) {
            return Collections.emptyList(); // Защита от null
        }
        return Arrays.stream(sizesString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty()) // Игнорируем пустые значения
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }



}
