package com.example.sneakershop.service;

import com.example.sneakershop.dto.AddToCartDTO;
import com.example.sneakershop.dto.CartDTO;
import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.CartItem;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.exception.NotFoundException;
import com.example.sneakershop.repositories.CartItemRepository;
import com.example.sneakershop.repositories.CartRepository;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final SneakerRepository sneakerRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

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


//    private CartDTO convertToDTO(Cart cart) {
//        CartDTO dto = new CartDTO();
//        dto.setId(cart.getId());
//        dto.setUserId(cart.getUser().getId());
//        dto.setTotalPrice(BigDecimal.valueOf(cart.getTotalPrice()));
//        dto.setSneakers(cart.getSneakers().stream()
//                .map(this::convertSneakerToDTO)
//                .collect(Collectors.toList()));
//        return dto;
//    }
//    private SneakerDTO convertSneakerToDTO(Sneaker sneaker) {
//        SneakerDTO dto = new SneakerDTO();
//        dto.setId(sneaker.getId());
//        dto.setName(sneaker.getName());
//        dto.setPrice(sneaker.getPrice());
//        return dto;
//    }

    public void addSneakerToCart(AddToCartDTO dto, String username) {
        // 1. Получаем пользователя по имени, unwrap Optional
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Получаем корзину пользователя или создаём новую
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        // 3. Находим кроссовок по ID
        Sneaker sneaker = sneakerRepository.findById(dto.getSneakerId())
                .orElseThrow(() -> new EntityNotFoundException("Sneaker not found: " + dto.getSneakerId()));

        // 4. Создаём и конфигурируем CartItem
        CartItem item = new CartItem();
        item.setSneaker(sneaker);
        item.setSize(dto.getSize());
        item.setCart(cart);


        // 5. Добавляем в корзину
        cart.getItems().add(item);

        // 6. Сохраняем корзину (CascadeType.ALL сохранит и CartItem)
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartItemId, Long userId) {
        Cart cart = getCartForUser(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found: " + cartItemId));

        // Проверяем, что элемент принадлежит корзине пользователя
        if (!cart.getItems().contains(cartItem)) {
            throw new IllegalArgumentException("Item does not belong to user's cart");
        }

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        updateCartTotalPrice(cart);
    }

    public void updateCartTotalPrice(Cart cart) {
        if (cart == null || cart.getItems() == null) {
            throw new IllegalArgumentException("Cart or cart items cannot be null");
        }
        double totalPrice = cart.getItems().stream()
                .filter(item -> item.getSneaker() != null)
                .mapToDouble(item -> item.getSneaker().getPrice())
                .sum();
        cart.setTotalPrice(totalPrice);
    }

    // Вспомогательный метод для получения ID пользователя из Principal
    public Long getUserId(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return user.getId();
    }

    @Transactional
    public void updateCartItemQuantity(Long cartItemId, int quantity, Long userId) {
        Cart cart = getCartForUser(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found: " + cartItemId));
        if (!cart.getItems().contains(cartItem)) {
            throw new IllegalArgumentException("Item does not belong to user's cart");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        updateCartTotalPrice(cart);
    }

    public int getCartItemsCount(Long userId) {
        Cart cart = getCartForUser(userId);
        return cart != null ? cart.getItems().size() : 0;
    }





}