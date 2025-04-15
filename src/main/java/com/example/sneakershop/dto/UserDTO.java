package com.example.sneakershop.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role; // Добавляем поле role


    public UserDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
