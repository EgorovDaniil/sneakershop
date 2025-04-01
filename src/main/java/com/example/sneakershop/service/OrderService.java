package com.example.sneakershop.service;

import com.example.sneakershop.dto.OrderDTO;
import com.example.sneakershop.entity.Cart;
import com.example.sneakershop.entity.Order;
import com.example.sneakershop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sneakershop.repositories.CartRepository;
import com.example.sneakershop.repositories.OrderRepository;
import com.example.sneakershop.repositories.UserRepository;

import java.util.List;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    // Создать заказ из корзины
    public OrderDTO createOrder(Long userId) {
        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Получаем корзину пользователя
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        // Создаем заказ
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());
        order.setSneakers(cart.getSneakers());
        order.setStatus("Created");  // Например, статус заказа

        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // Возвращаем DTO
        return new OrderDTO(savedOrder);
    }

    // Получить заказ по ID
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderDTO(order);
    }

    public OrderDTO getOrderByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new RuntimeException("Order not found for user");
        }

        // Берем первый заказ из списка, если нужно
        Order order = orders.get(0);
        return new OrderDTO(order);
    }

    // Обновить статус заказа
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Обновляем статус заказа
        order.setStatus(status);

        // Сохраняем обновленный заказ
        Order updatedOrder = orderRepository.save(order);

        // Возвращаем обновленную информацию в виде DTO
        return new OrderDTO(updatedOrder);
    }

}
