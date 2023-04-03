package com.example.demo.infrastructure.adapters.repositories;

import com.example.demo.domain.Status;
import com.example.demo.infrastructure.adapters.entities.AnimalEntity;
import com.example.demo.infrastructure.adapters.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface SpringPageableAnimalRepository extends PagingAndSortingRepository<AnimalEntity, UUID> {

    Page<AnimalEntity> findByCategoryEntity(CategoryEntity categoryEntity, Pageable pageable);

    Page<AnimalEntity> findByStatus(Status status, Pageable pageable);

    Page<AnimalEntity> findByCreatedAt(LocalDate createdAt, Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% )"
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContains(@Param("term") String term, Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% ) "
            + "AND a.category_code = :categoryCode "
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContainsAndCategoryCode(@Param("term") String term,
                                                                              @Param("categoryCode") Long categoryCode, Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% ) " +
            "AND a.category_code = :categoryCode " +
            "AND a.status = :status "
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContainsAndStatus(@Param("term") String term,
                                                                        @Param("status") String status, Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% ) " +
            "AND a.category_code = :categoryCode " +
            "AND a.created_at = :createdAt "
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContainsAndCreatedAt(@Param("term") String term,
                                                                           @Param("createdAt") LocalDate createdAt, Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% ) " +
            "AND a.category_code = :categoryCode " +
            "AND a.status = :status "
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatus(@Param("term") String term,
                                                                                       @Param("categoryCode") Long categoryCode,
                                                                                       @Param("status") String status,
                                                                                       Pageable pageable);

    @Query(value = "SELECT a.* FROM animal a WHERE ( a.name like %:term% OR a.description like %:term% ) " +
            "AND a.category_code = :categoryCode " +
            "AND a.status = :status " +
            "AND a.created_at = :createdAt"
            , nativeQuery = true)
    Page<AnimalEntity> findByNameContainsOrDescriptionContainsAndCategoryCodeAndStatusAndCreatedAt(@Param("term") String term,
                                                                                                   @Param("categoryCode") Long categoryCode,
                                                                                                   @Param("status") String status,
                                                                                                   @Param("createdAt") LocalDate createdAt, Pageable pageable);
}
