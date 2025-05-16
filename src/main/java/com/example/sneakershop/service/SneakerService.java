package com.example.sneakershop.service;

import com.example.sneakershop.dto.SneakerDTO;

import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.repositories.SneakerRepository;

import com.example.sneakershop.mapper.SneakerMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SneakerService {
    private final SneakerRepository sneakerRepository;
    private final SneakerMapper sneakerMapper;// Маппер, который будет использоваться для преобразования





    // Метод для сохранения кроссовок
    public SneakerDTO saveSneaker(SneakerDTO sneakerDTO) {
        Sneaker sneaker = sneakerMapper.sneakerDTOToSneaker(sneakerDTO);
        Sneaker savedSneaker = sneakerRepository.save(sneaker);  // Сохраняем сущность в базу
        return sneakerMapper.sneakerToSneakerDTO(savedSneaker);  // Преобразуем сохраненную сущность обратно в DTO
    }



    // Получить все кроссовки
    public List<SneakerDTO> getAllSneakers() {
        return sneakerRepository.findAll().stream()
                .map(sneakerMapper::sneakerToSneakerDTO)  // Используем маппер для преобразования сущности в DTO
                .collect(Collectors.toList());
    }

    // Получить кроссовки по категории
    public List<SneakerDTO> getSneakersByCategory(com.example.sneakershop.enums.SneakerCategory category) {
        return sneakerRepository.findByCategory(category).stream()
                .map(sneakerMapper::sneakerToSneakerDTO)  // Используем маппер для преобразования сущности в DTO
                .collect(Collectors.toList());
    }

    public SneakerDTO getSneakerById(Long id) {
        Optional<Sneaker> sneaker = sneakerRepository.findById(id);
        if (sneaker.isPresent()) {
            SneakerDTO sneakerDTO = sneakerMapper.sneakerToSneakerDTO(sneaker.get());
            System.out.println("Sneaker DTO created: " + sneakerDTO.getName() + " with sizes: " + sneakerDTO.getSizes());
            return sneakerDTO;
        } else {
            System.out.println("Sneaker with ID " + id + " not found");
            return null;  // Возвращаем null, если не нашли
        }
    }

    // Метод для получения популярных товаров (DTO)
    public List<SneakerDTO> getFeaturedSneakers() {
        List<Sneaker> sneakers = sneakerRepository.findTop5ByOrderByRatingDesc();  // Получаем сущности
        return sneakers.stream()
                .map(sneakerMapper::sneakerToSneakerDTO)  // Преобразуем сущности в DTO
                .collect(Collectors.toList());  // Возвращаем список DTO
    }


    public Sneaker convertToSneaker(SneakerDTO sneakerDTO) {
        Sneaker sneaker = new Sneaker();
        sneaker.setId(sneakerDTO.getId());
        sneaker.setName(sneakerDTO.getName());
        sneaker.setPrice(sneakerDTO.getPrice());
        sneaker.setBrand(sneakerDTO.getBrand());
        // Преобразуйте другие поля, если нужно
        return sneaker;
    }
    private SneakerDTO convertToSneakerDTO(Sneaker sneaker) {
        SneakerDTO sneakerDTO = new SneakerDTO();
        sneakerDTO.setId(sneaker.getId());
        sneakerDTO.setName(sneaker.getName());
        sneakerDTO.setPrice(sneaker.getPrice());
        sneakerDTO.setBrand(sneaker.getBrand());
        // Преобразуйте другие поля, если нужно
        return sneakerDTO;
    }

}