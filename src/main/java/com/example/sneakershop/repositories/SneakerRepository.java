package com.example.sneakershop.repositories;

import com.example.sneakershop.model.entity.Sneaker;
import com.example.sneakershop.enums.SneakerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
    // Находим кроссовки по бренду
    List<Sneaker> findByBrand(String brand);

    // Находим кроссовки по имени
    List<Sneaker> findByName(String name);

    // Находим кроссовки по цене
    List<Sneaker> findByPriceLessThanEqual(Double price);

    Optional<Sneaker> findAllById(Long id);

    List<Sneaker> findByCategory(SneakerCategory category);
    List<Sneaker> findTop5ByOrderByRatingDesc(); // Если нужно по рейтингу


}
