package com.example.sneakershop.service;

import com.example.sneakershop.dto.SneakerDTO;
import com.example.sneakershop.entity.Sneaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sneakershop.repositories.SneakerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SneakerService {

    @Autowired
    private SneakerRepository sneakerRepository;

    // Получить все кроссовки
    public List<SneakerDTO> getAllSneakers() {
        List<Sneaker> sneakers = sneakerRepository.findAll();
        return sneakers.stream()
                .map(sneaker -> new SneakerDTO(sneaker)) // Преобразуем в SneakerDTO
                .collect(Collectors.toList());
    }

    // Получить кроссовка по ID
    public SneakerDTO getSneakerById(Long id) {
        Sneaker sneaker = sneakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id " + id));
        return new SneakerDTO(sneaker); // Преобразуем в SneakerDTO
    }

    // Добавить новый кроссовок
    public SneakerDTO addSneaker(SneakerDTO sneakerDTO) {
        Sneaker sneaker = new Sneaker();
        sneaker.setName(sneakerDTO.getName());
        sneaker.setBrand(sneakerDTO.getBrand());
        sneaker.setPrice(sneakerDTO.getPrice());
        sneaker.setSize(sneakerDTO.getSize());
        sneaker.setImageUrl(sneakerDTO.getImageUrl());
        Sneaker savedSneaker = sneakerRepository.save(sneaker);
        return new SneakerDTO(savedSneaker); // Возвращаем DTO после сохранения
    }

    // Обновить существующего кроссовка
    public SneakerDTO updateSneaker(Long id, SneakerDTO sneakerDTO) {
        Sneaker sneaker = sneakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id " + id));

        sneaker.setName(sneakerDTO.getName());
        sneaker.setBrand(sneakerDTO.getBrand());
        sneaker.setPrice(sneakerDTO.getPrice());
        sneaker.setSize(sneakerDTO.getSize());
        sneaker.setImageUrl(sneakerDTO.getImageUrl());

        Sneaker updatedSneaker = sneakerRepository.save(sneaker);
        return new SneakerDTO(updatedSneaker); // Возвращаем обновленное DTO
    }

    // Удалить кроссовок
    public void deleteSneaker(Long id) {
        Sneaker sneaker = sneakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id " + id));

        sneakerRepository.delete(sneaker); // Удаляем кроссовок
    }
}
