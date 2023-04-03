package com.example.demo.domain.ports.repositories;

import com.example.demo.domain.Animal;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface AnimalRepositoryPort {

    Animal save(Animal animal, String filename);

    Animal save(Animal animal);

    Optional<AnimalEntity> findById(UUID animalId);

    Page<Animal> findByFilter(FilterParams filterParams);
}
