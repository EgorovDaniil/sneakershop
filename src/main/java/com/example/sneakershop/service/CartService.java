package com.example.sneakershop.service;

import com.example.sneakershop.dto.CartDTO;
import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.exception.NotFoundException;
import com.example.sneakershop.repositories.CartRepository;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final SneakerRepository sneakerRepository;
    private final UserRepository userRepository;
    // Получить корзину для пользователя (или создать новую)
    public Cart getCartForUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCartForUser(userId));
    }

    // Создание новой корзины для пользователя
    private Cart createNewCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }
    // Добавление кроссовка в корзину
    public void addSneakerToCart(Long cartId, Long sneakerId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Sneaker sneaker = sneakerRepository.findById(sneakerId)
                .orElseThrow(() -> new IllegalArgumentException("Sneaker not found"));

        // Добавляем кроссовок в список
        cart.getSneakers().add(sneaker);

        // Пересчитываем цену
        double totalPrice = cart.getSneakers().stream()
                .mapToDouble(Sneaker::getPrice)
                .sum();
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setTotalPrice(BigDecimal.valueOf(cart.getTotalPrice()));
        dto.setSneakers(cart.getSneakers().stream()
                .map(this::convertSneakerToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
    private SneakerDTO convertSneakerToDTO(Sneaker sneaker) {
        SneakerDTO dto = new SneakerDTO();
        dto.setId(sneaker.getId());
        dto.setName(sneaker.getName());
        dto.setPrice(sneaker.getPrice());
        return dto;
    }





}