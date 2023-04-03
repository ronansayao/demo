package com.example.demo.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class AnimalDTO {

    public AnimalDTO(String name, String description, CategoryDTO categoryDTO, String status, LocalDate creationDate) {
        this.name = name;
        this.description = description;
        this.category = categoryDTO;
        this.status = status;
        this.creationDate = creationDate;

    }

    private final String name;
    private final String description;
    private final CategoryDTO category;
    private final String status;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate creationDate;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryDTO getCategoryDTO() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }



}
