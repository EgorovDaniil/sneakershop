package com.example.sneakershop.controller;

import com.example.sneakershop.model.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ProfileController {
    @GetMapping(
            "/profile")
    public String userProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "sneaker/layouts/account"; // убедись, что файл account.html существует
    }
}
