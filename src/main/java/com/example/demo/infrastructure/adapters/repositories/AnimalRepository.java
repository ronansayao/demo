package com.example.demo.infrastructure.adapters.repositories;

import com.example.demo.domain.Animal;
import com.example.demo.domain.Status;
import com.example.demo.domain.dto.FilterParams;
import com.example.demo.domain.ports.repositories.AnimalRepositoryPort;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import com.example.demo.infrastructure.adapters.entities.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class AnimalRepository implements AnimalRepositoryPort {

    @Autowired
    private Environment environment;

    @Value("${base.url}")
    private String baseUrl;

    private final SpringAnimalRepository springAnimalRepository;
    private final SpringPageableAnimalRepository springPageableAnimalRepository;
    private final SpringCategoryRepository springCategoryRepository;

    public AnimalRepository(SpringAnimalRepository springAnimalRepository, SpringPageableAnimalRepository springPageableAnimalRepository, SpringCategoryRepository springCategoryRepository) {
        this.springAnimalRepository = springAnimalRepository;
        this.springPageableAnimalRepository = springPageableAnimalRepository;
        this.springCategoryRepository = springCategoryRepository;
    }

    public Animal save(Animal animal) {
        checkCategory(animal);
        AnimalEntity animalEntity;
        if (Objects.isNull(animal.getId())) {
            animalEntity = new AnimalEntity(animal);
        } else {
            animalEntity = this.springAnimalRepository.findById(animal.getId()).orElseThrow();
            animalEntity.update(animal);
        }

        return this.springAnimalRepository.save(animalEntity).toAnimal();

    }

    private void checkCategory(Animal animal) {
        if (animal.getCategory() != null) {
            if (springCategoryRepository.findById(animal.getCategory().getCode()).isEmpty()) {
                springCategoryRepository.save(new CategoryEntity(animal.getCategory()));
            }
        }
    }


    @Override
    public Animal save(Animal animal, String filename) {
        checkCategory(animal);
        AnimalEntity animalEntity;
        if (Objects.isNull(animal.getId())) {
            animalEntity = new AnimalEntity(animal, filename);
        } else {
            animalEntity = this.springAnimalRepository.findById(animal.getId()).orElseThrow();
            animalEntity.update(animal, filename);
        }
        AnimalEntity savedAnimal = this.springAnimalRepository.save(animalEntity);
        savedAnimal.setImageURL("http://" + baseUrl + ":" + environment.getProperty("local.server.port") + "/animal/" + savedAnimal.getId() + "/image/" + filename);
        return this.springAnimalRepository.save(savedAnimal).toAnimal();
    }

    @Override
    public Optional<AnimalEntity> findById(UUID animalId) {
        return springAnimalRepository.findById(animalId);
    }


    @Override
    public Page<Animal> findByFilter(FilterParams filterParams) {
        Pageable pageRequest = PageRequest.of(filterParams.getPage(), filterParams.getSize());
        Page<AnimalEntity> animalEntities = null;

        //1000
        if (filterParams.getTerm() != null && filterParams.getStatus() == null && filterParams.getCategory() == null && filterParams.getCreatedAt() == null) {
            animalEntities = springPageableAnimalRepository.findByNameContainsOrDescriptionContains(filterParams.getTerm(), pageRequest);
            //0100
        } else if (filterParams.getTerm() == null && filterParams.getStatus() != null && filterParams.getCategory() == null && filterParams.getCreatedAt() == null) {
            animalEntities = springPageableAnimalRepository.findByStatus(Status.fromString(filterParams.getStatus()), pageRequest);
            //0010
        } else if (filterParams.getTerm() == null && filterParams.getStatus() == null && filterParams.getCategory() != null && filterParams.getCreatedAt() == null) {
            Optional<CategoryEntity> categoryEntity = springCategoryRepository.findById(filterParams.getCategory());
            animalEntities = springPageableAnimalRepository.findByCategoryEntity(categoryEntity.orElseThrow(), pageRequest);
            //1010
        } else if ( filterParams.getTerm() != null && filterParams.getStatus() == null && filterParams.getCategory() != null && filterParams.getCreatedAt() == null) {
            animalEntities = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCode(filterParams.getTerm(), filterParams.getCategory(), pageRequest);
            //1100
        } else if (filterParams.getTerm() != null && filterParams.getStatus() != null && filterParams.getCategory() == null && filterParams.getCreatedAt() == null) {
            animalEntities = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndStatus(filterParams.getTerm(), filterParams.getStatus(), pageRequest);
            //0001
        } else if (filterParams.getTerm() == null && filterParams.getStatus() == null && filterParams.getCategory() == null && filterParams.getCreatedAt() != null) {
            animalEntities = springPageableAnimalRepository.findByCreatedAt(filterParams.getCreatedAt(), pageRequest);
            //1110
        } else if (filterParams.getTerm() != null && filterParams.getStatus() != null && filterParams.getCategory() != null && filterParams.getCreatedAt() == null) {
            animalEntities = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatus(filterParams.getTerm(), filterParams.getCategory(), Status.fromString(filterParams.getStatus()).toString(), pageRequest);
            //1111
        } else if (filterParams.getTerm() != null && filterParams.getStatus() != null && filterParams.getCategory() != null && filterParams.getCreatedAt() != null) {
            animalEntities = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatusAndCreatedAt(filterParams.getTerm(), filterParams.getCategory(), Status.fromString(filterParams.getStatus()).toString(), filterParams.getCreatedAt(), pageRequest);
        }

        return animalEntities.map(AnimalEntity::toAnimal);
    }
}
