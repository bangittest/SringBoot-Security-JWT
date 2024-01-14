package com.ra.dto.respose;

import com.ra.model.Category;
import com.ra.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String productName;
    private String description;
    private Float unitPrice;
    private String image;
    private String categoryName;
    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.unitPrice = product.getUnitPrice();
        this.image = product.getImage();
        this.categoryName = product.getCategory().getCategoryName();
    }
}
