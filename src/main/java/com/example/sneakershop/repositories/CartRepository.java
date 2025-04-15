package com.example.sneakershop.repositories;

import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

}