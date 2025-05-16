package com.example.sneakershop.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "sneaker_id")
    private Sneaker sneaker;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "size", nullable = false)
    private Integer size;
    @Column(name = "price", nullable = false)
    private Double price;
}