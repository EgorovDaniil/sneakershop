package com.example.sneakershop.dto;

import com.example.sneakershop.model.entity.Cart;
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
    private List<SneakerDTO> sneakers;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.username = cart.getUser().getUsername();
        this.totalPrice = BigDecimal.valueOf(cart.getTotalPrice());
        this.sneakers = cart.getSneakers().stream()
                .map(SneakerDTO::new)
                .collect(Collectors.toList());
    }


}
