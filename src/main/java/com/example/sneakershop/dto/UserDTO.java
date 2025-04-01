package com.example.sneakershop.dto;

import com.example.sneakershop.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    // Конструктор, который принимает User и преобразует его в UserDTO
    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Если ты хочешь, чтобы DTO принимал сущность User, можешь добавить этот конструктор
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }


}
