package com.example.sneakershop.service;

import com.example.sneakershop.dto.SneakerDTO;

import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.repositories.SneakerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SneakerService {
    private final SneakerRepository sneakerRepository;

    // Преобразование DTO в сущность
    private Sneaker convertToEntity(SneakerDTO sneakerDTO) {
        Sneaker sneaker = new Sneaker();
        sneaker.setName(sneakerDTO.getName());
        sneaker.setBrand(sneakerDTO.getBrand());
        sneaker.setPrice(sneakerDTO.getPrice());

        // Преобразование списка размеров в строку
        String sizesString = sneakerDTO.getSizes().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        sneaker.setSize(sizesString);  // Записываем строку в сущность

        sneaker.setImageUrl(sneakerDTO.getImageUrl());
        sneaker.setCategory(sneakerDTO.getCategory());

        return sneaker;
    }

    // Метод для сохранения кроссовок
    public Sneaker saveSneaker(SneakerDTO sneakerDTO) {
        Sneaker sneaker = convertToEntity(sneakerDTO);  // Преобразуем DTO в сущность
        return sneakerRepository.save(sneaker);  // Сохраняем сущность в базу
    }



    public List<SneakerDTO> getAllSneakers() {
        return sneakerRepository.findAll().stream()
                .map(SneakerDTO::new)
                .collect(Collectors.toList());
    }

    public List<SneakerDTO> getSneakersByCategory(com.example.sneakershop.enums.SneakerCategory category) {
        return sneakerRepository.findByCategory(category).stream()
                .map(SneakerDTO::new)
                .collect(Collectors.toList());
    }

    public SneakerDTO getSneakerById(Long id) {
        Sneaker sneaker = sneakerRepository.findById(id).orElse(null);
        if (sneaker == null) {
            System.err.println("[ERROR] Sneaker not found, id: " + id);
            return null; // или бросить исключение
        }
        return new SneakerDTO(sneaker);
    }
    // Метод для получения популярных товаров (DTO)
    public List<SneakerDTO> getFeaturedSneakers() {
        List<Sneaker> sneakers = sneakerRepository.findTop5ByOrderByRatingDesc(); // Получаем сущности
        return sneakers.stream()
                .map(SneakerDTO::new) // Преобразуем в DTO
                .collect(Collectors.toList()); // Возвращаем список DTO
    }

}