package com.example.sneakershop.controller;

import com.example.sneakershop.dto.UserDTO;
import com.example.sneakershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private UserService userService;


    private AuthenticationManager authenticationManager;

    // Показать форму логина
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Неверные учетные данные");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }

        return "sneaker/auth/login";  // Страница логина
    }

    // Обработка данных формы логина
    @PostMapping("/login")
    public String loginUser(UserDTO userDTO, Model model) {
        try {
            // Найти пользователя по имени
            UserDTO existingUser = userService.findByUsername(userDTO.getUsername());

            // Проверить правильность пароля
            if (!existingUser.getPassword().equals(userDTO.getPassword())) {
                model.addAttribute("error", "Неверный пароль");
                return "sneaker/auth/login";  // Возвращаем на форму логина
            }

            // Если логин успешен, перенаправляем на главную страницу
            return "redirect:/";  // Или на страницу профиля

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "sneaker/auth/login";  // Если ошибка, возвращаем на форму с сообщением об ошибке
        }
    }
}
