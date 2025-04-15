package com.example.sneakershop.controller;

import com.example.sneakershop.dto.CartDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final SneakerRepository sneakerRepository;


    @PostMapping("/cart/add/{sneakerId}")
    public String addToCart(@PathVariable("sneakerId") Long sneakerId, Principal principal) {
        Long userId = getUserId(principal);
        Cart cart = cartService.getCartForUser(userId);
        cartService.addSneakerToCart(cart.getId(), sneakerId);
        return "redirect:/cart";  // Перенаправляем на страницу корзины
    }

    @GetMapping("/cart")
    public String viewCart(Principal principal, Model model) {
        Long userId = getUserId(principal);
        Cart cart = cartService.getCartForUser(userId);
        model.addAttribute("cart", cart);
        return "sneaker/layouts/cart";  // Страница с корзиной
    }

    // Вспомогательный метод для получения ID пользователя из Principal
    private Long getUserId(Principal principal) {
        // Предполагаем, что пользователь аутентифицирован и у нас есть его ID
        User user = (User) ((Authentication) principal).getPrincipal();
        return user.getId();
    }
}