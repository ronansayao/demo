package com.example.demo.infrastructure.adaptes.repositories;

import com.example.demo.domain.Category;
import com.example.demo.domain.Status;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import com.example.demo.infrastructure.adapters.entities.CategoryEntity;
import com.example.demo.infrastructure.adapters.repositories.SpringAnimalRepository;
import com.example.demo.infrastructure.adapters.repositories.SpringCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class AnimalRepositoryAddUpdateTest {

    public static final String THE_ROTTWEILER = "The Rottweiler is a breed of domestic dog, regarded as medium-to-large or large.";
    public static final String LILI = "Lili";
    public static final String CATS = "Cats";
    public static final String DOGS = "Dogs";
    @Autowired
    private SpringAnimalRepository springAnimalRepository;

    @Autowired
    private SpringCategoryRepository springCategoryRepository;

    @BeforeEach
    public void setup() {
        springAnimalRepository.deleteAll();
        springCategoryRepository.save(new CategoryEntity(new Category(1L, DOGS)));
        springCategoryRepository.save(new CategoryEntity(new Category(2L, CATS)));
    }


    @Test
    public void createAnimal() {
        CategoryEntity categoryEntity = springCategoryRepository.findById(1L).orElseThrow();
        AnimalEntity animalEntity = getAnimal();
        animalEntity.setCategoryEntity(categoryEntity);
        this.springAnimalRepository.save(animalEntity);
        AnimalEntity animalEntityFound = this.springAnimalRepository.findById(animalEntity.getId()).orElseThrow();
        assertThat(animalEntityFound.getId()).isNotNull();
    }

    @Test
    public void updateAnimal() {
        CategoryEntity categoryEntity = springCategoryRepository.findById(1L).orElseThrow();
        AnimalEntity animalEntity = getAnimal();
        animalEntity.setCategoryEntity(categoryEntity);
        this.springAnimalRepository.save(animalEntity);
        categoryEntity = springCategoryRepository.findById(2L).orElseThrow();
        LocalDate newDate = LocalDate.now().plusDays(1);
        animalEntity.setName(LILI);
        animalEntity.setDescription(THE_ROTTWEILER);
        animalEntity.setStatus(Status.ADOPTED);
        animalEntity.setCategoryEntity(categoryEntity);
        animalEntity.setCreatedAt(newDate);
        this.springAnimalRepository.save(animalEntity);

        AnimalEntity animalEntityFound = this.springAnimalRepository.findById(animalEntity.getId()).orElseThrow();
        assertThat(animalEntityFound.getName()).isEqualTo(LILI);
        assertThat(animalEntityFound.getDescription()).isEqualTo(THE_ROTTWEILER);
        assertThat(animalEntityFound.getStatus()).isEqualTo(Status.ADOPTED);
        assertThat(animalEntityFound.getCategoryEntity().getName()).isEqualTo(CATS);
        assertThat(animalEntityFound.getCreatedAt()).isNotEqualTo(newDate);
    }


    private AnimalEntity getAnimal() {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setName("Meg");
        animalEntity.setDescription("Pincher is a type of dog developed originally as ratters on farms and for fighting or guarding, although today they are most often kept as pets. The breed is powerful and muscular with an elegant appearance. It originated from Germany.");
        animalEntity.setCategoryEntity(new CategoryEntity(new Category(1L, DOGS)));
        animalEntity.setStatus(Status.AVAILABLE);
        return animalEntity;
    }


}
