package com.example.demo.application.controller;

import com.example.demo.domain.Animal;
import com.example.demo.domain.dto.AnimalDTO;
import com.example.demo.domain.dto.AnimalStatusDTO;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.domain.ports.interfaces.AnimalServicePort;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("animal")
public class AnimalController {

    private final AnimalServicePort animalServicePort;


    public AnimalController(AnimalServicePort animalServicePort) {
        this.animalServicePort = animalServicePort;
    }


    @PostMapping
    Animal createAnimals(@RequestPart("animal") AnimalDTO animalDTO, @RequestPart("image") MultipartFile multipartFile) {
        return animalServicePort.createAnimal(animalDTO, multipartFile);
    }

    @PatchMapping("/status")
    Animal updateAnimalsStatus(@RequestBody AnimalStatusDTO animalStatusDTO) {
        return animalServicePort.updateAnimalStatus(animalStatusDTO);
    }

    @GetMapping
    Page<Animal> listAnimalsByFilter(@RequestPart("filterParams") FilterParams filterParams) {
        return animalServicePort.searchAnimalsByFilter(filterParams);
    }

    @GetMapping("{animalId}/image")
    @ResponseBody byte[] getImage(@RequestParam("animalId") UUID animalId) {
        return animalServicePort.getImage(animalId);
    }


}
