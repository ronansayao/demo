package com.example.demo.infrastructure.adaptes.repositories;

import com.example.demo.domain.Category;
import com.example.demo.domain.Status;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import com.example.demo.infrastructure.adapters.entities.CategoryEntity;
import com.example.demo.infrastructure.adapters.repositories.SpringAnimalRepository;
import com.example.demo.infrastructure.adapters.repositories.SpringCategoryRepository;
import com.example.demo.infrastructure.adapters.repositories.SpringPageableAnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnimalRepositorySearchTest {

    public static final String MEG = "Meg";
    public static final String LILI = "Lili";
    public static final String CATS = "Cats";
    public static final String DOGS = "Dogs";
    public static final String FISHES = "Fishes";


    @Autowired
    private SpringAnimalRepository springAnimalRepository;

    @Autowired
    private SpringPageableAnimalRepository springPageableAnimalRepository;

    @Autowired
    private SpringCategoryRepository springCategoryRepository;

    @BeforeEach
    public void setup() {
        springAnimalRepository.deleteAll();
        CategoryEntity categoryDogs = springCategoryRepository.save(new CategoryEntity(new Category(1L, DOGS)));
        CategoryEntity categoryCats = springCategoryRepository.save(new CategoryEntity(new Category(2L, CATS)));
        springCategoryRepository.save(new CategoryEntity(new Category(3L, FISHES)));
        springAnimalRepository.save(getAnimalPincher(UUID.fromString("e00f2a3b-bd22-4492-8345-0cd655b2c58d"),MEG, Status.AVAILABLE, categoryDogs));
        springAnimalRepository.save(getAnimalPincher(UUID.fromString("feeaa11b-88bb-4eab-82d6-030a5ed63fd6"),LILI, Status.ADOPTED, categoryDogs));
        springAnimalRepository.save(getAnimalPersian(UUID.fromString("f5bb3b67-a086-4586-8e15-352a187071cb"),"Garfield", Status.AVAILABLE, categoryCats));
        springAnimalRepository.save(getAnimalPersian(UUID.fromString("15418710-0882-4412-8506-abad981314c8"),"Frodo", Status.ADOPTED, categoryCats));
    }

    @Test
    public void searchAnimalsByNameFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContains
                (term, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameNotFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "JUPITER";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContains
                (term, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(0);
    }

    @Test
    public void searchAnimalsByNameOrDescriptionFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Pincher";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContains
                (term, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(2);
    }

    @Test
    public void searchAnimalsByNameOrDescriptionFoundName() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Meg";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContains
                (term, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndCategoryFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.
                findByNameContainsOrDescriptionContainsAndCategoryCode(term, 1L, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndOtherCategoryFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Garfield";
        Page<AnimalEntity> animals = springPageableAnimalRepository.
                findByNameContainsOrDescriptionContainsAndCategoryCode(term, 2L, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndCategoryNotFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCode(term, 2L, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(0);
    }

    @Test
    public void searchAnimalsByCategoryFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByCategoryEntity(new CategoryEntity(new Category(1L, "Dogs")), pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(2);
    }

    @Test
    public void searchAnimalsByStatusFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByStatus(Status.AVAILABLE, pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(2);
    }

    @Test
    public void searchAnimalsByCategoryNotFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByCategoryEntity(new CategoryEntity(new Category(3L, "Fishes")), pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(0);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndStatusFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatus(term, 1L, Status.ADOPTED.toString(), pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndStatusNotFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatus(term, 1L, Status.AVAILABLE.toString(),pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(0);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndStatusAndCreatedAtFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatusAndCreatedAt(
                term, 1L, Status.ADOPTED.toString(), LocalDate.now(), pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(1);
    }

    @Test
    public void searchAnimalsByNameAndDescriptionAndStatusAndCreatedAtNotFound() {
        Pageable pageRequest = PageRequest.of(0, 10);
        String term = "Lili";
        Page<AnimalEntity> animals = springPageableAnimalRepository.findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatusAndCreatedAt(
                term,1L, Status.ADOPTED.toString(), LocalDate.now().plusDays(1), pageRequest);
        assertThat(animals.getContent().size()).isEqualTo(0);
    }

    private AnimalEntity getAnimalPincher(UUID id, String name, Status status, CategoryEntity categoryEntity) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(id);
        animalEntity.setName(name);
        animalEntity.setDescription("Pincher is a type of dog developed originally as ratters on farms and for fighting or guarding, although today they are most often kept as pets. The breed is powerful and muscular with an elegant appearance. It originated from Germany.");
        animalEntity.setCategoryEntity(categoryEntity);
        animalEntity.setStatus(status);
        return animalEntity;
    }
    private AnimalEntity getAnimalPersian(UUID id, String name, Status status, CategoryEntity categoryEntity) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(id);
        animalEntity.setName(name);
        animalEntity.setDescription("The Persian cat, also known as the Persian longhair, is a long-haired breed of cat characterized by a round face and short muzzle.");
        animalEntity.setCategoryEntity(categoryEntity);
        animalEntity.setStatus(status);
        return animalEntity;
    }

}
