package com.example.sneakershop.dto;

import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class CartDTO {

    private Long id;
    private Long userId;
    private String username;
    private BigDecimal totalPrice;
    private List<CartSneakerDTO> sneakers;



    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.username = cart.getUser().getUsername();

        // ⚠️ Логирование перед маппингом — поможет отладить null
        System.out.println("Items in cart: " + cart.getItems().size());
        for (CartItem item : cart.getItems()) {
            System.out.println("Item ID: " + item.getId() +
                    ", Sneaker: " + (item.getSneaker() != null ? item.getSneaker().getName() : "null") +
                    ", Size: " + item.getSize());
        }

        // 💡 Безопасное преобразование только непустых элементов
        this.sneakers = cart.getItems().stream()
                .filter(item -> item.getSneaker() != null)  // игнорируем сломанные связи
                .map(item -> new CartSneakerDTO(item.getId(), item.getSneaker(), item.getSize()))
                .collect(Collectors.toList());

        this.totalPrice = getTotalPrice(cart);
    }
    private BigDecimal getTotalPrice(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> item.getSneaker() != null)
                .map(item -> BigDecimal.valueOf(item.getSneaker().getPrice() * item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}
