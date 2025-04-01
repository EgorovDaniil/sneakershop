package com.example.sneakershop.dto;

import com.example.sneakershop.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private Long userId;
    private Double totalPrice;
    private List<SneakerDTO> sneakers;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.totalPrice = cart.getTotalPrice();
        this.sneakers = cart.getSneakers().stream()
                .map(sneaker -> new SneakerDTO(sneaker))
                .collect(Collectors.toList());
    }



}
