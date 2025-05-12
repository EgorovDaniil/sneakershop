package com.example.sneakershop.controller;
import com.example.sneakershop.dto.CartSneakerDTO;
import com.example.sneakershop.dto.OrderDTO;
import com.example.sneakershop.dto.OrderSneakerDTO;
import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.service.CartService;
import com.example.sneakershop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CartService cartService;
    private final OrderService  orderService;


    @GetMapping
    public String showCheckoutForm(@AuthenticationPrincipal User user, Model model) {
        System.out.println("Checkout requested for user: " + user.getUsername() + " (ID: " + user.getId() + ")");

        Cart cart = cartService.getCartForUser(user.getId());
        System.out.println("Retrieved cart: " + (cart != null ? "ID=" + cart.getId() : "null"));

        if (cart != null) {
            System.out.println("Cart items: " + cart.getItems().size());
            cart.getItems().forEach(item ->
                    System.out.println("Item: " + item.getSneaker().getName() + ", Size: " + item.getSize()));
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getTotalPrice()); // Добавляем общую стоимость


        List<OrderSneakerDTO> orderSneakers = cart.getItems().stream()
                .map(item -> {
                    OrderSneakerDTO dto = new OrderSneakerDTO(
                            item.getSneaker().getId(),
                            item.getSneaker().getName(),
                            item.getSneaker().getBrand(),
                            item.getSneaker().getPrice(),
                            item.getSize(),
                            item.getSneaker().getImageUrl(),
                            item.getQuantity()

                    );
                    System.out.println("Created OrderSneakerDTO: " + dto);
                    return dto;
                })
                .collect(Collectors.toList());

        model.addAttribute("orderSneakers", orderSneakers);
        return "sneaker/layouts/checkout";
    }



    @PostMapping("/process")
    public String processCheckout(@AuthenticationPrincipal User user,
                                  @RequestParam("cartId") Long cartId,
                                  RedirectAttributes redirectAttributes) {
        try {
            OrderDTO order = orderService.createOrderFromCart(cartId, user.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно оформлен");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при оформлении заказа: " + e.getMessage());
            return "redirect:/checkout";
        }
    }


    @GetMapping("/success")
    public String showSuccessPage() {
        return "sneaker/layouts/payment-success";
    }
}