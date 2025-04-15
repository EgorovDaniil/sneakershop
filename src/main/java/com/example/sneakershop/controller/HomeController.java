package com.example.sneakershop.controller;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.service.SneakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final SneakerRepository sneakerRepository;
    private final SneakerService sneakerService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "SneakerShop - Главная");
        model.addAttribute("cartCount", 0);

        List<Sneaker> featured = sneakerRepository.findAll();if (featured.isEmpty()) {
            System.out.println("Нет популярных товаров!");
        } else {
            System.out.println("Популярные товары получены: " + featured.size());
            featured.forEach(s -> System.out.println("Image URL: " + s.getImageUrl()));  // Логируем imageUrl
        }
        model.addAttribute("featuredSneakers", featured);
        model.addAttribute("emptyFeatured", featured.isEmpty());

        return "sneaker/layouts/index"; // путь до templates/layouts/index.html
    }
    @GetMapping("/sneaker/{id}")
    public String viewSneaker(@PathVariable Long id, Model model) {
        try {
            SneakerDTO sneaker = sneakerService.getSneakerById(id);
            if (sneaker == null) {
                return "redirect:/?error=sneaker_not_found";
            }

            // Проверка и установка изображения по умолчанию
            if (sneaker.getImageUrl() == null || sneaker.getImageUrl().isEmpty()) {
                sneaker.setImageUrl("default.png");
            }

            model.addAttribute("sneaker", sneaker);
            return "sneaker/layouts/sneaker-details";
        } catch (Exception e) {
            return "redirect:/?error=server_error";
        }
    }


}
