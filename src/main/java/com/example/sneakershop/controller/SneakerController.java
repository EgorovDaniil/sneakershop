package com.example.sneakershop.controller;
import com.example.sneakershop.dto.SneakerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.sneakershop.service.SneakerService;

import java.util.List;

@Controller
@RequestMapping("/sneakers")

public class SneakerController {
    @Autowired
    private final SneakerService sneakerService;

    public SneakerController(SneakerService sneakerService) {
        this.sneakerService = sneakerService;
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "sneaker/layouts/contacts";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "sneaker/layouts/index";  // Эта строка указывает Spring искать файл "index.html" в папке /templates
    }

}
