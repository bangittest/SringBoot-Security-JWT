package com.ra.repository;

import com.ra.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByCategoryName(String categoryName);
    Page<Category>findAllByCategoryNameContainsIgnoreCase(Pageable pageable,String categoryName);
    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.status = CASE WHEN c.status = true THEN false ELSE true END WHERE c.id = :categoryId")
    void updateCategoryStatus(@Param("categoryId") Long categoryId);

    boolean existsById(Long id);
    @Query("SELECT COUNT(c) FROM Category c WHERE c.status = true")
    long countCategoriesByStatusTrue();
}
