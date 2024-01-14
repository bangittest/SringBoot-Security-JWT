package com.ra.service.category;

import com.ra.dto.request.CategoryRequestDTO;
import com.ra.dto.respose.CategoryResponseDTO;
import com.ra.exception.CategoryAlreadyExistsException;
import com.ra.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryAlreadyExistsException;
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO,Long id);
    void updateCategoryStatus(Long categoryId);

}
