package com.example.demo.infrastructure.adaptes.repositories;

import com.example.demo.domain.Category;
import com.example.demo.infrastructure.adapters.entities.CategoryEntity;
import com.example.demo.infrastructure.adapters.repositories.SpringCategoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private SpringCategoryRepository springCategoryRepository;

    @Test
    public void createCategory() {
        CategoryEntity categoryEntity = new CategoryEntity(getCategoryCats());
        springCategoryRepository.save(categoryEntity);
        CategoryEntity categoryEntityFound = springCategoryRepository.findByName("Cats").get();
        assertThat(categoryEntity.getCode()).isEqualTo(categoryEntityFound.getCode());
    }

    @Test
    public void findByNameCategory() {
        CategoryEntity categoryEntity = new CategoryEntity(getCategoryDogs());
        springCategoryRepository.save(categoryEntity);
        CategoryEntity categoryEntityFound = springCategoryRepository.findByName("Dogs").get();
        assertThat(categoryEntity.getName()).isEqualTo(categoryEntityFound.getName());
    }

    private Category getCategoryCats() {
        return new Category(2L, "Cats");
    }

    private Category getCategoryDogs() {
        return new Category(1L, "Dogs");
    }


}
