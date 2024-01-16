package com.ra.controller;

import com.ra.dto.respose.home.ProductResponseDTO;
import com.ra.dto.respose.home.ProductResponseDesDTO;
import com.ra.dto.respose.product.ProductResponseDto;
import com.ra.exception.CategoryNotFoundException;
import com.ra.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product")
    public ResponseEntity<?>findAll(@RequestParam(defaultValue = "5",name = "limit" ) Integer limit,
                                    @RequestParam(name = "page",defaultValue = "0") Integer noPage,
                                    @RequestParam(name = "keywords" ,defaultValue = "",required = false)String keywords,
                                    @RequestParam(name = "sort" ,defaultValue = "id")String sort,
                                    @RequestParam(name = "order",defaultValue = "ASC")String order){
        Pageable pageable;
        if (order.equals("desc")){
            pageable=PageRequest.of(noPage,limit, Sort.by(sort).descending());
        }else {
            pageable=PageRequest.of(noPage,limit, Sort.by(sort).ascending());
        }
        Page<ProductResponseDTO>productDTOS=productService.findAllProduct(pageable,keywords);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<?>findProductCategory(@PathVariable String categoryId) {
       try{
           Long cateId= Long.valueOf(categoryId);
           List<ProductResponseDTO>productDTOS=productService.findAllCategoryId(cateId);
           return new ResponseEntity<>(productDTOS,HttpStatus.OK);
       }catch (CategoryNotFoundException e){
           return new ResponseEntity<>("not found id " + categoryId, HttpStatus.NOT_FOUND);
       }
       catch (Exception e) {
           return new ResponseEntity<>("vui long nhap so error", HttpStatus.BAD_REQUEST);
       }
    }
    @GetMapping("/product/new-products")
    public ResponseEntity<?>findProduct5(){
        List<ProductResponseDTO>productDTOS=productService.findTop5Products();
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?>findProductDescriptionId(@PathVariable String productId){
        try{
            Long proId= Long.valueOf(productId);
            ProductResponseDesDTO productResponseDesDTO=productService.findProductResponseID(proId);
            return new ResponseEntity<>(productResponseDesDTO,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("vui long nhap so error", HttpStatus.BAD_REQUEST);
        }
    }
}
