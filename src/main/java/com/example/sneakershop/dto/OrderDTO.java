package com.example.sneakershop.dto;

import com.example.sneakershop.model.entity.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private Double totalPrice;
    private LocalDateTime orderDate;

    private List<SneakerDTO> sneakers;

    public OrderDTO() {
    }
    // Конструктор для преобразования сущности Order в OrderDTO
    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.sneakers = order.getSneakers().stream()
                .map(SneakerDTO::new)
                .collect(Collectors.toList());
    }


}
