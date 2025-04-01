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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "status", nullable = false, length = 20)
    private String status;  // Статус заказа, например, "новый", "в процессе", "завершен"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Связь с пользователем

    @ManyToMany
    @JoinTable(
            name = "order_sneaker",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "sneaker_id"))
    private List<Sneaker> sneakers;  // Список кроссовок в заказе

}
