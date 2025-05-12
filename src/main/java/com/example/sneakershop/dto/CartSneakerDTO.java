package com.example.sneakershop.dto;

import com.example.sneakershop.model.entity.Sneaker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSneakerDTO {
    private Long sneakerId; // ID кроссовка
    private String name;
    private String brand;
    private Double price;
    private String imageUrl;
    private Integer selectedSize; // <-- ВАЖНО: выбранный размер

    private Long cartItemId; // Добавлено
    private int size;        // Добавлено
    private Integer quantity = 1; // Добавленное поле




    // Конструктор для преобразования сущности CartItem в DTO
    public CartSneakerDTO(Sneaker sneaker, Integer selectedSize) {
        this.sneakerId = sneaker.getId();
        this.name = sneaker.getName();
        this.brand = sneaker.getBrand();
        this.price = sneaker.getPrice();
        this.imageUrl = sneaker.getImageUrl();
        this.selectedSize = selectedSize;
        this.quantity = 1;

    }


    public CartSneakerDTO(Long cartItemId, Sneaker sneaker, int size) {
        this.cartItemId = cartItemId;
        this.sneakerId = sneaker.getId();
        this.name = sneaker.getName();
        this.brand = sneaker.getBrand();
        this.price = sneaker.getPrice();
        this.imageUrl = sneaker.getImageUrl();
        this.size = size;
        this.quantity = 1;

    }


}
