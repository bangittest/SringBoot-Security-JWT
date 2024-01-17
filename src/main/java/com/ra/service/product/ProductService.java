package com.ra.service.product;

import com.ra.dto.request.ProductRequestDTO;
import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.home.ProductResponseDTO;
import com.ra.dto.respose.home.ProductResponseDesDTO;
import com.ra.dto.respose.product.ProductResponseDto;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.exception.ProductExistsException;
import com.ra.exception.QuantityException;
import com.ra.exception.SizeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto>findAll();
    List<ProductResponseDto>findAllSearch(String productName);
    List<ProductResponseDTO>findAllSearchHome(String productName);
    Page<ProductResponseDTO>findAllProductPaginateHome(Pageable pageable);
    Page<ProductResponseDto>findAllPaginate(Pageable pageable);
    List<ProductResponseDTO>findAllCategoryId(Long categoryId);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws ProductExistsException;
    ProductResponseDto findById(Long id);
    ProductResponseDto updateProduct(ProductRequestdto productRequestdto, Long productId) throws SizeNotFoundException, ColorExceptionNotFound, QuantityException;
    Page<ProductResponseDTO>findAllProduct(Pageable pageable, String productName);
    List<ProductResponseDTO>findTop5Products();
    long countProductsByStatusTrue();

    ProductResponseDto createProductssss(ProductRequestdto productRequestdto) throws ProductExistsException, SizeNotFoundException, ColorExceptionNotFound, QuantityException;
    void changeStatus(Long id);
    ProductResponseDesDTO findProductResponseID(Long productId);

}
