package com.example.sneakershop.service;

import com.example.sneakershop.dto.CartDTO;
import com.example.sneakershop.entity.Cart;
import com.example.sneakershop.entity.Sneaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sneakershop.repositories.CartRepository;
import com.example.sneakershop.repositories.SneakerRepository;

import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SneakerRepository sneakerRepository;

    // Получить корзину пользователя по ID
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id " + userId));
        return new CartDTO(cart);
    }

    // Добавить товар в корзину
    public CartDTO addSneakerToCart(Long userId, Long sneakerId) {
        // Получаем корзину пользователя
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id " + userId));

        // Получаем кроссовки по ID
        Sneaker sneaker = sneakerRepository.findById(sneakerId)
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id " + sneakerId));

        // Добавляем кроссовки в корзину
        cart.getSneakers().add(sneaker);
        cart.setTotalPrice(cart.getTotalPrice() + sneaker.getPrice());

        // Сохраняем обновленную корзину
        Cart updatedCart = cartRepository.save(cart);
        return new CartDTO(updatedCart);  // Возвращаем обновленную корзину как CartDTO
    }

    // Удалить товар из корзины
    public CartDTO removeSneakerFromCart(Long userId, Long sneakerId) {
        // Получаем корзину пользователя
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id " + userId));

        // Получаем кроссовки по ID
        Sneaker sneaker = sneakerRepository.findById(sneakerId)
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id " + sneakerId));

        // Удаляем кроссовки из корзины
        cart.getSneakers().remove(sneaker);
        cart.setTotalPrice(cart.getTotalPrice() - sneaker.getPrice());

        // Сохраняем обновленную корзину
        Cart updatedCart = cartRepository.save(cart);
        return new CartDTO(updatedCart);  // Возвращаем обновленную корзину как CartDTO
    }

    // Обновить корзину
    public CartDTO updateCart(Long userId, CartDTO cartDTO) {
        // Получаем корзину пользователя
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id " + userId));

        // Обновляем данные корзины
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setSneakers(cartDTO.getSneakers() != null ? cartDTO.getSneakers().stream()
                .map(sneakerDTO -> new Sneaker(sneakerDTO.getId(), sneakerDTO.getName(), sneakerDTO.getBrand(),
                        sneakerDTO.getPrice(), sneakerDTO.getSize(), sneakerDTO.getImageUrl()))
                .collect(Collectors.toList()) : cart.getSneakers());

        // Сохраняем обновленную корзину
        Cart updatedCart = cartRepository.save(cart);
        return new CartDTO(updatedCart);  // Возвращаем обновленную корзину как CartDTO
    }


}
