package com.example.sneakershop.dto;

import com.example.sneakershop.entity.Sneaker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SneakerDTO {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String size;
    private String imageUrl;


    public SneakerDTO(Sneaker sneaker) {
        this.id = sneaker.getId();
        this.name = sneaker.getName();
        this.brand = sneaker.getBrand();
        this.price = sneaker.getPrice();
        this.size = sneaker.getSize();
        this.imageUrl = sneaker.getImageUrl();
    }

}
