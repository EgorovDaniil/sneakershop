package com.example.sneakershop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Transient
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "cart_sneaker",
//            joinColumns = @JoinColumn(name = "cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "sneaker_id")
//    )
//    private List<Sneaker> sneakers;  // Список кроссовок в корзине

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();


    // Метод для добавления товара в корзину с размером
    public void addSneakerWithSize(Sneaker sneaker, int size) {
        // Проверяем, есть ли такой товар с этим размером
        CartItem cartItem = new CartItem();
        cartItem.setSneaker(sneaker);
        cartItem.setSize(size);
        cartItem.setCart(this);  // Устанавливаем связь с корзиной

        // Проверка на уникальность товара с размером
        boolean exists = items.stream()
                .anyMatch(item -> item.getSneaker().getId().equals(sneaker.getId()) && item.getSize() == size);

        if (!exists) {
            items.add(cartItem);
        }

    }


    public double getTotalPrice() {
        return items.stream()
                .map(item -> BigDecimal.valueOf(item.getSneaker().getPrice())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }








}

