package com.example.sneakershop.controller;

import com.example.sneakershop.dto.AddToCartDTO;
import com.example.sneakershop.dto.CartDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String addToCart(@PathVariable("sneakerId") Long sneakerId,
                            @RequestParam("size") int size,
                            @AuthenticationPrincipal User user, Principal principal) {

        System.out.println("Adding sneaker to cart: Sneaker ID = " + sneakerId + ", Size = " + size);
        Long userId = cartService.getUserId(principal);
        cartService.addSneakerToCart(new AddToCartDTO(sneakerId, size), user.getUsername());
        return "redirect:/cart";  // Перенаправляем на страницу корзины
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long sneakerId,
                                 @AuthenticationPrincipal User user) {
        cartService.removeItemFromCart(sneakerId, user.getId());
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String viewCart(@AuthenticationPrincipal User user, Model model) {
      //  Long userId = cartService.getUserId(principal);
        Cart cart = cartService.getCartForUser(user.getId());
        CartDTO cartDTO = new CartDTO(cart);
        model.addAttribute("cart", cartDTO); // Вся корзина (для общей суммы)
        model.addAttribute("cartItems" , cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());


        return "sneaker/layouts/cart";  // Страница с корзиной
    }


}