package com.example.sneakershop.repositories;

import com.example.sneakershop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Находим корзину для конкретного пользователя
    Optional<Cart> findByUserId(Long userId);
}
