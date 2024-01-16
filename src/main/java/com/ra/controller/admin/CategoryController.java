package com.ra.controller.admin;

import com.ra.dto.request.CategoryRequestDTO;
import com.ra.dto.respose.CategoryResponseDTO;
import com.ra.exception.CategoryAlreadyExistsException;
import com.ra.exception.CategoryNotFoundException;
import com.ra.exception.YourCustomBadRequestException;
import com.ra.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    public ResponseEntity<?>findAll(){
        List<CategoryResponseDTO>list=categoryService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) throws CategoryAlreadyExistsException {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?>findById(@PathVariable String categoryId){
        try {
            Long cateId= Long.valueOf(categoryId);
            CategoryResponseDTO categoryResponseDTO=categoryService.findById(cateId);
            return new ResponseEntity<>(categoryResponseDTO,HttpStatus.OK);
        } catch (YourCustomBadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?>updateCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO,@PathVariable String categoryId){
        try {
            Long cateId= Long.valueOf(categoryId);
            CategoryResponseDTO categoryResponseDTO=categoryService.updateCategory(categoryRequestDTO,cateId);
            return new ResponseEntity<>(categoryResponseDTO,HttpStatus.OK);
        }catch (CategoryNotFoundException e){
            return new ResponseEntity<>("categoryId not found",HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<?>updateStatus(@PathVariable String categoryId) throws Exception {
        try {
            Long cateId= Long.valueOf(categoryId);
            categoryService.updateCategoryStatus(cateId);
            return new ResponseEntity<>("status updated",HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        }
    }

}
