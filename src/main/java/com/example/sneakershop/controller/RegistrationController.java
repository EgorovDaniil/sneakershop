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
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    // Показать форму регистрации
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "sneaker/auth/register"; // Убедись, что файл существует
    }

    // Обработка данных формы регистрации
    @PostMapping("/register")
    public String registerUser(UserDTO userDTO, Model model) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "sneaker/auth/register";
        }

        try {
            userService.registerUser(userDTO);
            return "redirect:/auth/login"; // После успешной регистрации
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "sneaker/auth/register";
        }
    }

}
