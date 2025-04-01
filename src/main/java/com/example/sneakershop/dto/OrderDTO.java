package com.example.sneakershop.dto;

import com.example.sneakershop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private Double totalPrice;
    private List<SneakerDTO> sneakers;
    // Конструктор для преобразования сущности Order в OrderDTO
    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.totalPrice = order.getTotalPrice();
        this.sneakers = order.getSneakers().stream()
                .map(sneaker -> new SneakerDTO(sneaker))  // Преобразуем Sneaker в SneakerDTO
                .collect(Collectors.toList());
    }


}
