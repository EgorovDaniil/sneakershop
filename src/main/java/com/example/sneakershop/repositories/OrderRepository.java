package com.example.sneakershop.repositories;

import com.example.sneakershop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Находим все заказы для конкретного пользователя
    List<Order> findByUserId(Long userId);

    // Находим все заказы с определенным статусом
    List<Order> findByStatus(String status);
}
