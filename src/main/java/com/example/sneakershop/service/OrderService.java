package com.example.sneakershop.service;

import com.example.sneakershop.dto.OrderDTO;
import com.example.sneakershop.model.entity.Order;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.repositories.OrderRepository;
import com.example.sneakershop.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("CREATED");
        order.setTotalPrice(0.0); // Будет обновлено при добавлении товаров

        Order savedOrder = orderRepository.save(order);
        return new OrderDTO(savedOrder);
    }

    public List<OrderDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return new OrderDTO(orderRepository.save(order));
    }
}