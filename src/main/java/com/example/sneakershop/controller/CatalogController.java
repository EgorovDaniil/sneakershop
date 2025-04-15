package com.example.sneakershop.controller;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.enums.SneakerCategory;
import com.example.sneakershop.service.CartService;
import com.example.sneakershop.service.SneakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final SneakerService sneakerService;
    private final CartService cartService;

    @GetMapping("/catalog")
    public String viewCatalog(@RequestParam(value = "category", required = false) SneakerCategory category, Model model) {
        List<SneakerDTO> sneakers = (category == null)
                ? sneakerService.getAllSneakers()
                : sneakerService.getSneakersByCategory(category);

        model.addAttribute("sneakers", sneakers);
        return "sneaker/layouts/catalog"; // убедись, что этот шаблон существует в templates/
    }
}
