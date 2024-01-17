package com.ra.service.category;

import com.ra.dto.request.CategoryRequestDTO;
import com.ra.dto.respose.CategoryResponseDTO;
import com.ra.dto.respose.category.CategoryResponseDto;
import com.ra.exception.CategoryAlreadyExistsException;
import com.ra.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto>findAllHome();
    Page<CategoryResponseDTO>findAllPaginateSort(Pageable pageable);
    List<CategoryResponseDTO>findAllSearch(String name);
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryAlreadyExistsException;
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO,Long id);
    void updateCategoryStatus(Long categoryId);
    long countCategoriesByStatusTrue();

}
