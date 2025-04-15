package com.example.sneakershop.model.entity;

import com.example.sneakershop.enums.SneakerCategory;
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
@Table(name = "sneaker")
public class Sneaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "size" , nullable = false , length = 250)
    private String size;

    @Column(name = "image_url", length = 255)
    private String imageUrl = "/images/";
    @Column(name = "rating" , nullable = false)
    private Double rating;

    @Enumerated(EnumType.STRING)
    private SneakerCategory category;




}
