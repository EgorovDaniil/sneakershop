package com.example.sneakershop.repositories;

import com.example.sneakershop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Метод для поиска пользователя по имени
    Optional<User> findByUsername(String username);

    // Метод для поиска пользователя по email
    Optional<User> findByEmail(String email);

    // Метод для проверки существования пользователя по имени
    boolean existsByUsername(String username);

    // Метод для проверки существования пользователя по email
    boolean existsByEmail(String email);

}
