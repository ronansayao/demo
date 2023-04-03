package com.example.demo.domain.adapter.service;

import com.example.demo.domain.Animal;
import com.example.demo.domain.Status;
import com.example.demo.domain.dto.AnimalDTO;
import com.example.demo.domain.dto.AnimalStatusDTO;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.domain.ports.file.FileAdapterPort;
import com.example.demo.domain.ports.interfaces.AnimalServicePort;
import com.example.demo.domain.ports.repositories.AnimalRepositoryPort;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class AnimalServiceImpl implements AnimalServicePort {

    private final AnimalRepositoryPort animalRepository;

    private final FileAdapterPort fileAdapterPort;

    public AnimalServiceImpl(AnimalRepositoryPort animalRepository, FileAdapterPort fileAdapterPort) {
        this.animalRepository = animalRepository;
        this.fileAdapterPort = fileAdapterPort;
    }
    @Override
    public Animal createAnimal(AnimalDTO animalDTO, MultipartFile file) {
        Animal animal = animalRepository.save(new Animal(animalDTO), file.getOriginalFilename());
        try {
            fileAdapterPort.saveFile(animal.getId(), file);
            animal = animalRepository.save(animal, file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return animal;
    }

    @Override
    public Animal updateAnimalStatus(AnimalStatusDTO animalStatusDTO) {
        AnimalEntity animal = animalRepository.findById(animalStatusDTO.animalId()).orElseThrow();
        animal.setStatus(Status.fromString(animalStatusDTO.status()));
        return animalRepository.save(animal.toAnimal());
    }

    @Override
    public Page<Animal> searchAnimalsByFilter(FilterParams filterParams) {
        return animalRepository.findByFilter(filterParams);
    }

    @Override
    public byte[] getImage(UUID animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow().toAnimal();
        return fileAdapterPort.getFile(animalId, animal.getFilename());
    }
}
