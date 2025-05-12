package com.example.sneakershop.service;

import com.example.sneakershop.dto.OrderDTO;
import com.example.sneakershop.model.entity.*;
import com.example.sneakershop.repositories.CartRepository;
import com.example.sneakershop.repositories.OrderRepository;
import com.example.sneakershop.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Transactional
    public OrderDTO createOrderFromCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Корзина не найдена"));
        if (!cart.getUser().getId().equals(userId)) {
            throw new RuntimeException("Доступ запрещен");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("CREATED");
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setSneaker(cartItem.getSneaker());
                    orderItem.setSize(cartItem.getSize());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getSneaker().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // Очищаем корзину
        cart.getItems().clear();
        cartRepository.save(cart);

        return new OrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return new OrderDTO(orderRepository.save(order));
    }



}