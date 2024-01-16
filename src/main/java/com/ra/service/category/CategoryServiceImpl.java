package com.ra.service.category;

import com.ra.dto.request.CategoryRequestDTO;
import com.ra.dto.respose.CategoryResponseDTO;
import com.ra.exception.CategoryAlreadyExistsException;
import com.ra.exception.CategoryNotFoundException;
import com.ra.exception.YourCustomBadRequestException;
import com.ra.model.Category;
import com.ra.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponseDTO> findAll() {
        List<Category>categoryList=categoryRepository.findAll();
        return categoryList
                .stream().map(CategoryResponseDTO::new).toList();
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryAlreadyExistsException {
        if (categoryRepository.existsByCategoryName(categoryRequestDTO.getCategoryName())){
            if (categoryRepository.existsByCategoryName(categoryRequestDTO.getCategoryName())) {
                throw new CategoryAlreadyExistsException("Category with name " + categoryRequestDTO.getCategoryName() + " already exists");
            }
        }
        Category category=new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setStatus(categoryRequestDTO.getStatus());
        categoryRepository.save(category);
        return new CategoryResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return new CategoryResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setStatus(categoryRequestDTO.getStatus());
        categoryRepository.save(category);

        return new CategoryResponseDTO(category);
    }
    @Transactional
    @Override
    public void updateCategoryStatus(Long categoryId) {
        if (categoryRepository.existsById(categoryId)){
            categoryRepository.updateCategoryStatus(categoryId);
        }else {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public long countCategoriesByStatusTrue() {
        return categoryRepository.countCategoriesByStatusTrue();
    }
}
