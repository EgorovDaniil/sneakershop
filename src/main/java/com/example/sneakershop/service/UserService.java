package com.example.sneakershop.service;

import com.example.sneakershop.dto.UserDTO;
import com.example.sneakershop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sneakershop.repositories.UserRepository;


@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    // Создать нового пользователя
    public UserDTO createUser(UserDTO userDTO) {
        // Проверка, чтобы избежать повторного имени или email
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Преобразуем DTO в Entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Сохраняем пользователя в базу данных
        User savedUser = userRepository.save(user);

        // Возвращаем сохраненного пользователя в виде DTO
        return new UserDTO(savedUser);
    }

    // Получить пользователя по ID
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(user);
    }

    // Получить пользователя по имени
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(user);
    }

    // Обновить информацию о пользователе
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Обновляем информацию о пользователе
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Сохраняем обновленного пользователя
        User updatedUser = userRepository.save(user);

        // Возвращаем обновленного пользователя в виде DTO
        return new UserDTO(updatedUser);
    }

    // Удалить пользователя
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }




}
