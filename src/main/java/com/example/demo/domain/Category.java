package com.example.demo.domain;

import com.example.demo.domain.dto.CategoryDTO;

public class Category {

    private Long code;
    private String name;

    public Category(CategoryDTO categoryDTO) {
        this.code = categoryDTO.code();
        this.name = categoryDTO.name();
    }

    public Category(Long code, String name) {
        this.code = code;
        this.name = name;
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
}
