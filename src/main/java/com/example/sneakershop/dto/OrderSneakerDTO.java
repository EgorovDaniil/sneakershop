package com.example.sneakershop.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSneakerDTO {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private Integer size;
    private String imageUrl;
    private Integer quantity;

}