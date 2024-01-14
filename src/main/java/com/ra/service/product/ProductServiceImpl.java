package com.ra.service.product;

import com.ra.dto.request.ProductRequestDTO;
import com.ra.dto.request.product.ProductRequestdto;
import com.ra.dto.respose.ProductResponseDTO;
import com.ra.dto.respose.product.ProductDTO;
import com.ra.exception.CategoryNotFoundException;
import com.ra.exception.ProductExistsException;
import com.ra.exception.ProductNotFoundException;
import com.ra.model.Category;
import com.ra.model.Color;
import com.ra.model.Product;
import com.ra.model.Size;
import com.ra.repository.CategoryRepository;
import com.ra.repository.ProductRepository;
import com.ra.service.color.ColorService;
import com.ra.service.size.SizeService;
import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<ProductResponseDTO> findAll() {
        List<Product>productList=productRepository.findAll();
        return productList.stream().map(ProductResponseDTO::new).toList();
    }

    @Override
    public List<ProductDTO> findAllCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        List<Product>list=productRepository.findAllByCategory(category);
        return list.stream().map((ProductDTO::new)).toList();
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
    public ProductResponseDTO findById(Long id) {
        ProductResponseDTO productResponseDTO=
                productRepository.findById(id).map(product -> new ProductResponseDTO(product))
                .orElseThrow(()->new ProductNotFoundException("Product not found with ID:" +id));
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO,Long productId){
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("product not found with id" +productId));
        product.setId(productId);
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
        return new ProductResponseDTO(product);
    }

    @Override
    public Page<ProductDTO> findAllProduct(Pageable pageable,String productName) {
        Page<Product>list=productRepository.findAllByProductNameContainsIgnoreCase(pageable,productName);
        return list.map(ProductDTO::new);
    }

    @Override
    public List<ProductDTO> findTop5Products() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        List<Product>products=productRepository.findAll(pageRequest).getContent();
        return products.stream().map(ProductDTO::new).toList();
    }

    @Override
    public Product createProductssss(ProductRequestdto productRequestdto) throws ProductExistsException {
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
        Long categoryId = productRequestdto.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        product.setCategory(category);
//        String fileName=uploadService.uploadImage(productRequestdto.getImage());
//        product.setImage(fileName);
        product.setColors(colorSet);
        product.setSizes(sizes);
        return productRepository.save(product);
    }
}
