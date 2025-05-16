package com.example.sneakershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с корзиной
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Связь с кроссовком
    @ManyToOne
    @JoinColumn(name = "sneaker_id")
    private Sneaker sneaker;

    // Размер кроссовка, выбранный пользователем
    @Column(name = "size", nullable = false)
    private Integer size;
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1; // По умолчанию 1



}
