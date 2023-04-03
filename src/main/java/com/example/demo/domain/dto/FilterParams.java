package com.example.demo.domain.dto;

import java.time.LocalDate;

public class FilterParams {


    private String term;
    private Long category;
    private String status;


    private LocalDate createdAt;

    private int page;

    private int size;


    public String getTerm() {
        return term;
    }

    public Long getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


}
