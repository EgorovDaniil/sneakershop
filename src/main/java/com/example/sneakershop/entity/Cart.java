package com.example.sneakershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne // связь с пользователем (корзина принадлежит одному пользователю).
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Связь с пользователем

    @ManyToMany // связь с кроссовками (в корзине может быть несколько кроссовок).
    @JoinTable(
            name = "cart_sneaker",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "sneaker_id"))
    private List<Sneaker> sneakers;  // Список кроссовок в корзине
}

