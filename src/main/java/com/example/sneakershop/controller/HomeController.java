package com.example.sneakershop.controller;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.model.entity.Cart;
import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.model.entity.User;
import com.example.sneakershop.repositories.SneakerRepository;
import com.example.sneakershop.service.CartService;
import com.example.sneakershop.service.SneakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final CartService cartService;

    @GetMapping("/")
    public String home(Model model , @AuthenticationPrincipal User user) {
        model.addAttribute("pageTitle", "SneakerShop - Главная");
        model.addAttribute("cartCount", 0);
        int cartCount = 0;
        if (user != null) {
            Cart cart = cartService.getCartForUser(user.getId());
            cartCount = cart.getItems().size();
        }

        List<SneakerDTO> featured = sneakerService.getFeaturedSneakers();
        if (featured.isEmpty()) {
            System.out.println("Нет популярных товаров!");
        } else {
            System.out.println("Популярные товары получены: " + featured.size());
            featured.forEach(s -> System.out.println("Image URL: " + s.getImageUrl()));  // Логируем imageUrl
        }
        model.addAttribute("featuredSneakers", featured);
        model.addAttribute("emptyFeatured", featured.isEmpty());
        model.addAttribute("cartCount", cartCount);


        return "sneaker/layouts/index"; // путь до templates/layouts/index.html
    }
    @GetMapping("/sneaker/{id}")
    public String viewSneaker(@PathVariable Long id, Model model) {
        try{
            SneakerDTO sneaker = sneakerService.getSneakerById(id);
            // Добавим логирование
            System.out.println("Sneaker details:");
            System.out.println("ID: " + sneaker.getId());
            System.out.println("Name: " + sneaker.getName());
            System.out.println("Sizes: " + sneaker.getSizes());

            if (sneaker == null) {
                return "redirect:/?error=sneaker_not_found";
            }
            // Логируем полученные данные
            System.out.println("Sneaker ID: " + sneaker.getId());
            System.out.println("Sneaker Name: " + sneaker.getName());

            // Проверка и установка изображения по умолчанию
            if (sneaker.getImageUrl() == null || sneaker.getImageUrl().isEmpty()) {
                sneaker.setImageUrl("default.png");
            }

            model.addAttribute("sneaker", sneaker);

            return "sneaker/layouts/sneaker-details";
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/?error=sneaker_not_found";
        }

    }


}
