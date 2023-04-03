package com.example.demo.infrastructure.adapters.entities;

import com.example.demo.domain.Animal;
import com.example.demo.domain.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "animal")
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_code", referencedColumnName = "code")
    private CategoryEntity categoryEntity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="created_at",updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;
    private String fileName;

    private String imageURL;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public AnimalEntity() {

    }

    public AnimalEntity(Animal animal, String filename) {
        this.name = animal.getName();
        this.description = animal.getDescription();
        this.categoryEntity = new CategoryEntity(animal.getCategory());
        this.status = animal.getStatus();
        this.createdAt = animal.getCreationDate();
        this.fileName = filename;
    }

    public AnimalEntity(Animal animal) {
        this.name = animal.getName();
        this.description = animal.getDescription();
        this.categoryEntity = new CategoryEntity(animal.getCategory());
        this.status = animal.getStatus();
        this.createdAt = animal.getCreationDate();
    }

    public void update(Animal animal, String fileName) {
        this.name = animal.getName();
        this.description = animal.getDescription();
        this.categoryEntity = new CategoryEntity(animal.getCategory());
        this.status = animal.getStatus();
        this.createdAt = animal.getCreationDate();
        this.fileName = fileName;
    }

    public void update(Animal animal) {
        this.name = animal.getName();
        this.description = animal.getDescription();
        this.categoryEntity = new CategoryEntity(animal.getCategory());
        this.status = animal.getStatus();
        this.createdAt = animal.getCreationDate();
    }
    public Animal toAnimal() {
        return new Animal(this.id, this.name, this.description, this.categoryEntity.toCategory(), this.status, this.createdAt, this.imageURL, this.fileName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalEntity that = (AnimalEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
