package com.ra.service.product;

import com.ra.dto.request.ProductRequestDTO;
import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.ProductResponseDTO;
import com.ra.dto.respose.product.ProductDTO;
import com.ra.exception.ProductExistsException;
import com.ra.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO>findAll();
    List<ProductDTO>findAllCategoryId(Long categoryId);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws ProductExistsException;
    ProductResponseDTO findById(Long id);
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO,Long productId);
    Page<ProductDTO>findAllProduct(Pageable pageable,String productName);
    List<ProductDTO>findTop5Products();

    Product createProductssss(ProductRequestdto productRequestdto) throws ProductExistsException;

}
