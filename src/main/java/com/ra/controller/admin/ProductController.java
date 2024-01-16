package com.ra.controller.admin;

import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.product.ProductResponseDto;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.exception.ProductExistsException;
import com.ra.exception.QuantityException;
import com.ra.exception.SizeNotFoundException;
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
        List<ProductResponseDto> productResponseDTOList=productService.findAll();
        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);
    }
    @PostMapping("/product")
    public ResponseEntity<?>createProductsss(@ModelAttribute @Valid ProductRequestdto productRequestdto) throws ProductExistsException, SizeNotFoundException, ColorExceptionNotFound, QuantityException {
        ProductResponseDto product=productService.createProductssss(productRequestdto);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
//    @PostMapping("/product")
//    public ResponseEntity<?>createProduct(@ModelAttribute @Valid ProductRequestDTO productRequestDTO) throws ProductExistsException {
//            ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
//            return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
//    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<?>findProductById(@PathVariable String productId){
        try {
            Long id= Long.valueOf(productId);
            ProductResponseDto productResponseDto=productService.findById(id);
            return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(
            @ModelAttribute @Valid ProductRequestdto productRequestdto,
            @PathVariable String productId) throws SizeNotFoundException, ColorExceptionNotFound {
        try {
            Long id= Long.valueOf(productId);
            ProductResponseDto productResponseDTO = productService.updateProduct(productRequestdto,id);
            return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        } catch (QuantityException e) {
            throw new RuntimeException(e);
        }
    }


    @PatchMapping("product/{productId}")
    public ResponseEntity<?>changeStatus(@PathVariable String productId){
        try {
            Long id= Long.valueOf(productId);
            productService.changeStatus(id);
            return new ResponseEntity<>("đổi trang thái " + id + "thành công",HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
        }
    }
}
