package com.ra.controller.admin;

import com.ra.exception.CategoryNotFoundException;
import com.ra.exception.ProductNotFoundException;
import com.ra.model.Category;
import com.ra.model.Product;
import com.ra.repository.CategoryRepository;
import com.ra.repository.ProductRepository;
import com.ra.service.category.CategoryService;
import com.ra.service.order.OrderService;
import com.ra.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/admin")
public class DashBoardController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping("dash-board/total")
    public ResponseEntity<?> getTotal() {
        BigDecimal total=orderService.getTotal();
        return new ResponseEntity<>("tổng doanh thu = " + total,HttpStatus.OK);
    }
    @GetMapping("dash-board/sales")
    public ResponseEntity<?> getTotalSalesByMonth(@RequestParam String month) {
       try {
           int month1= Integer.parseInt(month);
           if (month1 < 1 || month1 > 12) {
               return new ResponseEntity<>("Invalid month. Please enter a value between 1 and 12.", HttpStatus.BAD_REQUEST);
           }
           BigDecimal totalSales = orderService.getTotalSalesByMonth(month1);
           if (totalSales==null){
               return new ResponseEntity<>("total sale thang " + month + " = 0" ,HttpStatus.OK);
           }
           return new ResponseEntity<>("total sale thang " + month + "=" + totalSales,HttpStatus.OK);
       }catch (NumberFormatException e) {
           return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
       }
    }
    @GetMapping("dash-board/categoryID")
    public ResponseEntity<String> getTotalByCategory(@RequestParam Long categoryId) {
        try {
            Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException("category id not found"));
            if (category == null) {
                return new ResponseEntity<>("Invalid category ID.", HttpStatus.BAD_REQUEST);
            }
            BigDecimal totalCategory = orderService.getTotalByCategoryId(category);

            return new ResponseEntity<>("Total revenue for category " + category.getCategoryName() + " is " + totalCategory, HttpStatus.OK);
        }catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("dash-board/productID")
    public ResponseEntity<String> getTotalProduct(@RequestParam String productId) {
       try {
           Long IdProduct = Long.parseLong(productId);
           Product product=productRepository.findById(IdProduct).orElseThrow(()->new ProductNotFoundException("category id not found"));
           if (product == null) {
               return new ResponseEntity<>("Invalid category ID.", HttpStatus.BAD_REQUEST);
           }
           BigDecimal totalProduct = orderService.getTotalByProduct(product);

           return new ResponseEntity<>("Total revenue for category " + product.getProductName() + " is " + totalProduct, HttpStatus.OK);
       }catch (NumberFormatException e) {
           return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
       }
    }
    @GetMapping("dash-board/category")
    public ResponseEntity<?> getCategory() {
        long total=categoryService.countCategoriesByStatusTrue();
        return new ResponseEntity<>("total so luong category = " + total,HttpStatus.OK);
    }
    @GetMapping("dash-board/product")
    public ResponseEntity<?> getProduct() {
        long total=productService.countProductsByStatusTrue();
        return new ResponseEntity<>("total so luong product= " + total,HttpStatus.OK);
    }
}
