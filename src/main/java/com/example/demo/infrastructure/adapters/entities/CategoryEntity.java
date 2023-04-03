package com.example.demo.infrastructure.adapters.entities;

import com.example.demo.domain.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;


    private String name;

    public CategoryEntity() {
    }

    public CategoryEntity(Category category) {
        this.code = category.getCode();
        this.name = category.getName();
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category toCategory() {
        return new Category(this.code, this.name);
    }
}
