package com.ra.controller.admin;

import com.ra.dto.request.ProductRequestDTO;
import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.ProductResponseDTO;
import com.ra.exception.ProductExistsException;
import com.ra.model.Product;
import com.ra.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public ResponseEntity<?>findAll(){
        List<ProductResponseDTO> productResponseDTOList=productService.findAll();
        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);
    }
    @PostMapping("/product")
    public ResponseEntity<?>createProduct(@ModelAttribute @Valid ProductRequestDTO productRequestDTO) throws ProductExistsException {
            ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?>findProductById(@PathVariable Long productId){
           ProductResponseDTO productResponseDTO=productService.findById(productId);
           return new ResponseEntity<>(productResponseDTO,HttpStatus.OK);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(
            @ModelAttribute @Valid ProductRequestDTO productRequestDTO,
            @PathVariable Long productId) {
            ProductResponseDTO productResponseDTO = productService.updateProduct(productRequestDTO,productId);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/product/add")
    public ResponseEntity<?>createProductsss(@ModelAttribute @Valid ProductRequestdto productRequestdto) throws ProductExistsException {
           Product product=productService.createProductssss(productRequestdto);
           return new ResponseEntity<>(product,HttpStatus.CREATED);


    }
}
