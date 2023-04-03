package com.example.demo.domain.ports.interfaces;

import com.example.demo.domain.Animal;
import com.example.demo.domain.dto.AnimalDTO;
import com.example.demo.domain.dto.AnimalStatusDTO;
import com.example.demo.domain.dto.FilterParams;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AnimalServicePort {


    Animal createAnimal(AnimalDTO animal, MultipartFile file) ;

    Animal updateAnimalStatus(AnimalStatusDTO animalStatusDTO);

    Page<Animal> searchAnimalsByFilter(FilterParams filterParams);

    byte[] getImage(UUID animalId);

}
