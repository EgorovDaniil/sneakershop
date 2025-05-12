package com.example.sneakershop.controller;

import com.example.sneakershop.dto.OrderDTO;
import com.example.sneakershop.dto.OrderSneakerDTO;
import com.example.sneakershop.model.entity.Cart;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal User user,
                              @RequestParam("cartId") Long cartId,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (cartService.isCartEmpty(cartId)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Корзина пуста");
                return "redirect:/cart";
            }

            // Получаем корзину до создания заказа
            Cart cart = cartService.getCartForUser(user.getId());
            List<OrderSneakerDTO> orderSneakers = cart.getItems().stream()
                    .map(item -> new OrderSneakerDTO(
                            item.getSneaker().getId(),
                            item.getSneaker().getName(),
                            item.getSneaker().getBrand(),
                            item.getSneaker().getPrice(),
                            item.getSize(),
                            item.getSneaker().getImageUrl(),
                            item.getQuantity()
                    ))
                    .collect(Collectors.toList());

            // Создаем заказ
            OrderDTO order = orderService.createOrderFromCart(cartId, user.getId());

            // Добавляем атрибуты для отображения
            model.addAttribute("orderSneakers", orderSneakers);
            model.addAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно создан");

            return "sneaker/layouts/checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании заказа");
            return "redirect:/cart";
        }
    }




    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "order-confirmation";
    }
}
