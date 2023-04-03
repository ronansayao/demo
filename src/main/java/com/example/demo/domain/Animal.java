package com.example.demo.domain;

import com.example.demo.domain.dto.AnimalDTO;

import java.time.LocalDate;
import java.util.UUID;

public class Animal {

    private UUID id;
    private final String name;
    private final String description;
    private final Category category;
    private Status status;
    private final LocalDate creationDate;
    private String imageURL;



    private String filename;

    public Animal(UUID id, String name, String description, Category category, Status status, LocalDate creationDate, String imageURL, String filename) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.creationDate = creationDate;
        this.imageURL = imageURL;
        this.filename = filename;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Category getCategory() {
        return category;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getFilename() {
        return filename;
    }

    public Animal(AnimalDTO animalDTO) {
        this.name = animalDTO.getName();
        this.description = animalDTO.getDescription();
        this.category = new Category(animalDTO.getCategoryDTO());
        this.status = Status.fromString(animalDTO.getStatus());
        this.creationDate = animalDTO.getCreationDate();
    }
}
