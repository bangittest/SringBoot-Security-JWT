package com.ra.repository;

import com.ra.model.Category;
import com.ra.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Boolean existsByProductName(String productName);
    List<Product>findAllByCategory(Category category);
    Page<Product>findAllByProductNameContainsIgnoreCase(Pageable pageable,String productName);
    @Modifying
    @Transactional
    @Query("SELECT COUNT(p) FROM Product p where p.status=true")
    long countProductsByStatusTrue();
}
