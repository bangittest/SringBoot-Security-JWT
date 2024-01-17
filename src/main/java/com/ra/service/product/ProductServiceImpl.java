package com.ra.service.product;

import com.ra.dto.request.ProductRequestDTO;
import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.home.ProductResponseDTO;
import com.ra.dto.respose.home.ProductResponseDesDTO;
import com.ra.dto.respose.product.ProductResponseDto;
import com.ra.exception.*;
import com.ra.model.Category;
import com.ra.model.Color;
import com.ra.model.Product;
import com.ra.model.Size;
import com.ra.repository.CategoryRepository;
import com.ra.repository.ProductRepository;
import com.ra.service.color.ColorService;
import com.ra.service.size.SizeService;
import com.ra.service.upload.UploadService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Override
    public List<ProductResponseDto> findAll() {
        List<Product>productList=productRepository.findAll();
        return productList.stream().map(ProductResponseDto::new).toList();
    }

    @Override
    public List<ProductResponseDto> findAllSearch(String productName) {
        List<Product>productList=productRepository.findAllByProductNameContainsIgnoreCase(productName);
        return productList.stream().map(ProductResponseDto::new).toList();
    }

    @Override
    public List<ProductResponseDTO> findAllSearchHome(String productName) {
        List<Product>productList=productRepository.findAllByProductNameContainsIgnoreCase(productName);
        return productList.stream().map(ProductResponseDTO::new).toList();
    }

    @Override
    public Page<ProductResponseDTO> findAllProductPaginateHome(Pageable pageable) {
        Page<Product>list=productRepository.findAllByStatusTrue(pageable);
        return list.map(ProductResponseDTO::new);
    }

    @Override
    public Page<ProductResponseDto> findAllPaginate(Pageable pageable) {
        Page<Product>list=productRepository.findAll(pageable);
        return list.map(ProductResponseDto::new);
    }

    @Override
    public List<ProductResponseDTO> findAllCategoryId(Long categoryId) {
          Category category = categoryRepository.findById(categoryId)
                  .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
          List<Product>list=productRepository.findAllByCategory(category);
          return list.stream().map((ProductResponseDTO::new)).toList();
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws ProductExistsException {
        if (productRepository.existsByProductName(productRequestDTO.getProductName())){
            throw new ProductExistsException("Category with name " + productRequestDTO.getProductName() + " already exists");
        }
        Product product=new Product();
        product.setProductName(productRequestDTO.getProductName());
        product.setSku(UUID.randomUUID().toString());
        product.setDescription(productRequestDTO.getDescription());
        product.setUnitPrice(productRequestDTO.getUnitPrice());
        Long categoryId = productRequestDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        product.setCategory(category);
        String fileName=uploadService.uploadImage(productRequestDTO.getImage());
        product.setImage(fileName);
        productRepository.save(product);
        return new ProductResponseDTO(product) ;
    }

    @Override
    public ProductResponseDto findById(Long id) {
        ProductResponseDto productResponseDto=
                productRepository.findById(id).map(ProductResponseDto::new)
                .orElseThrow(()->new ProductNotFoundException("Product not found with ID:" +id));
        return productResponseDto;
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestdto productRequestdto, Long productId) throws SizeNotFoundException, ColorExceptionNotFound, QuantityException {
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("product not found with id" +productId));
        Set<Color>colorSet=new HashSet<>();
        Set<String>colors=productRequestdto.getColors();
        if (productRequestdto.getColors()==null){
            colorSet = product.getColors();
        }else {
            for (String colorName : colors) {
                Color color = colorService.findByColorName(colorName);
                if (color != null) {
                    colorSet.add(color);
                }
            }
        }
        Set<Size>sizes=new HashSet<>();
        Set<String>sizeNew=productRequestdto.getSizes();
        if (productRequestdto.getSizes()==null){
            sizes=product.getSizes();
        }else {
            for (String size:sizeNew) {
                Size size1=sizeService.findByName(size);
                if (size1!=null){
                    sizes.add(size1);
                }
            }
        }
        if (productRequestdto.getImage()==null){
            product.setImage(product.getImage());
        }else {
//            product.setImage(productRequestDTO.getImage().getOriginalFilename());
            String fileName=uploadService.uploadImage(productRequestdto.getImage());
            product.setImage(fileName);
        }
        product.setId(productId);
        if (productRequestdto.getProductName()==null){
            product.setProductName(product.getProductName());
        }else {
            product.setProductName(productRequestdto.getProductName());
        }

        product.setSku(UUID.randomUUID().toString());
        if (productRequestdto.getDescription()==null){
            product.setDescription(product.getDescription());
        }else {
            product.setDescription(productRequestdto.getDescription());
        }
        if (productRequestdto.getUnitPrice()==null){
            product.setUnitPrice(product.getUnitPrice());
        }else {
            product.setUnitPrice(productRequestdto.getUnitPrice());
        }
        try {
          Long categoryId = Long.valueOf(productRequestdto.getCategoryId());
          Category category = categoryRepository.findById(categoryId)
                  .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
          product.setCategory(category);
       }catch (NumberFormatException e){
          throw new QuantityException("vui long nhap so vao danh muc", HttpStatus.BAD_REQUEST);
       }
//        String fileName=uploadService.uploadImage(productRequestDTO.getImage());
//        product.setImage(fileName);
        product.setColors(colorSet);
        product.setSizes(sizes);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Override
    public Page<ProductResponseDTO> findAllProduct(Pageable pageable, String productName) {
        Page<Product>list=productRepository.findAllByProductNameContainsIgnoreCaseAndStatusTrue(pageable,productName);
        return list.map(ProductResponseDTO::new);
    }

    @Override
    public List<ProductResponseDTO> findTop5Products() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        List<Product>products=productRepository.findAll(pageRequest).getContent();
        return products.stream().map(ProductResponseDTO::new).toList();
    }

    @Override
    public long countProductsByStatusTrue() {
        return productRepository.countProductByStatusTrue();
    }

    @Override
    public ProductResponseDto createProductssss(ProductRequestdto productRequestdto) throws ProductExistsException, SizeNotFoundException, ColorExceptionNotFound, QuantityException {
        Set<Color>colorSet=new HashSet<>();
        Set<String>colors=productRequestdto.getColors();
        if (productRequestdto.getColors()==null){
            colorSet = Collections.emptySet();
        }else {
            for (String colorName : colors) {
                Color color = colorService.findByColorName(colorName);
                if (color != null) {
                    colorSet.add(color);
                }
            }
        }
        Set<Size>sizes=new HashSet<>();
        Set<String>sizeNew=productRequestdto.getSizes();
        if (productRequestdto.getSizes()==null){
            sizes=Collections.emptySet();
        }else {
            for (String size:sizeNew) {
                Size size1=sizeService.findByName(size);
                if (size1!=null){
                    sizes.add(size1);
                }
            }
        }
        if (productRepository.existsByProductName(productRequestdto.getProductName())){
            throw new ProductExistsException("product with name " + productRequestdto.getProductName() + " already exists");
        }
        Product product=new Product();
        product.setProductName(productRequestdto.getProductName());
        product.setSku(UUID.randomUUID().toString());
        product.setDescription(productRequestdto.getDescription());
        product.setUnitPrice(productRequestdto.getUnitPrice());
        try {
            Long categoryId = Long.valueOf(productRequestdto.getCategoryId());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
            product.setCategory(category);
        }catch (Exception e){
            throw new QuantityException("vui long nhap so vao danh muc", HttpStatus.BAD_REQUEST);
        }
        if (productRequestdto.getImage()==null){
            product.setImage(null);
            System.out.println("image not found");
        }else {
            String fileName=uploadService.uploadImage(productRequestdto.getImage());
            product.setImage(fileName);
        }
        product.setColors(colorSet);
        product.setSizes(sizes);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Override
    public void changeStatus(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("id product not found"));
        product.setStatus(!product.getStatus());
        productRepository.save(product);
    }

    @Override
    public ProductResponseDesDTO findProductResponseID(Long productId) {
        ProductResponseDesDTO productResponseDesDTO=productRepository
                .findById(productId).map((ProductResponseDesDTO::new)).orElseThrow(()->new ProductNotFoundException("Product not found with ID:" + productId));
        return productResponseDesDTO;
    }
}
