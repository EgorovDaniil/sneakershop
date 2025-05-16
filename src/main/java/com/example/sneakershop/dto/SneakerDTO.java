package com.example.sneakershop.dto;

import com.example.sneakershop.mapper.SneakerMapperImpl;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.enums.SneakerCategory;
import com.example.sneakershop.util.SneakerMapperUtil;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Getter
@Setter
public class SneakerDTO  {

    private Long id;
    private String name;
    private String brand;
    private Double price;
    private List<Integer> sizes; // Например: [38, 39, 40, 42]
    private Integer size;        // выбранный размер

    private String imageUrl;
    private SneakerCategory category;

    public SneakerDTO() {
    }

    public SneakerDTO(Sneaker sneaker) {
        this.id = sneaker.getId();
        this.name = sneaker.getName();
        this.brand = sneaker.getBrand();
        this.price = sneaker.getPrice();
        this.sizes = SneakerMapperUtil.parseSizes(sneaker.getSize());
        this.imageUrl = sneaker.getImageUrl();
        this.category = sneaker.getCategory();

        // Добавим логирование для отладки
        System.out.println("Sneaker ID: " + id);
        System.out.println("Sizes string from DB: " + sneaker.getSize());
        System.out.println("Parsed sizes: " + sizes);

    }

}


