package com.example.demo.infrastructure.adapters.repositories;

import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringAnimalRepository extends JpaRepository<AnimalEntity, UUID> {

    Optional<AnimalEntity> findById(UUID id);


}
