package com.example.sneakershop.entity;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , unique = true , length = 50)
    private String username;

    @Column(name  = "email", unique = true , length = 100)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;
}
