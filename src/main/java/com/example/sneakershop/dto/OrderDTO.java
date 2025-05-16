package com.example.sneakershop.dto;
import com.example.sneakershop.model.entity.Order;
import com.example.sneakershop.model.entity.OrderItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private List<OrderSneakerDTO> sneakers;
    public OrderDTO() {

    }
    public OrderDTO(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.sneakers = order.getOrderItems().stream()
                .map(item -> new OrderSneakerDTO(
                        item.getSneaker().getId(),
                        item.getSneaker().getName(),
                        item.getSneaker().getBrand(),
                        item.getPrice(),
                        item.getSize(),
                        item.getSneaker().getImageUrl(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
        this.totalPrice = this.sneakers.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

    }
}