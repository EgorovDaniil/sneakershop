package com.example.sneakershop.controller;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.enums.SneakerCategory;
import com.example.sneakershop.service.SneakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sneakershop.model.entity.Sneaker;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
@RequiredArgsConstructor
public class SneakerController {
    private final SneakerService sneakerService;


    @GetMapping
    public ResponseEntity<List<SneakerDTO>> getAllSneakers() {
        return ResponseEntity.ok(sneakerService.getAllSneakers());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SneakerDTO>> getSneakersByCategory(
            @PathVariable String category) {
        SneakerCategory sneakerCategory = SneakerCategory.valueOf(category.toUpperCase());
        return ResponseEntity.ok(sneakerService.getSneakersByCategory(sneakerCategory));
    }


    // Метод для создания нового кроссовка
    @PostMapping
    public ResponseEntity<SneakerDTO> createSneaker(@RequestBody SneakerDTO sneakerDTO) {
        SneakerDTO savedSneakerDTO = sneakerService.saveSneaker(sneakerDTO);  // Сохраняем и сразу возвращаем DTO
        return ResponseEntity.ok(savedSneakerDTO);  // Возвращаем сохраненный кроссовок
    }
}