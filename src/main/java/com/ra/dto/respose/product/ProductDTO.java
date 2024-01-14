package com.ra.dto.respose.product;

import com.ra.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Long id;
    private String productName;
    private String description;
    private Float unitPrice;
    private String image;
    private String categoryName;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.unitPrice = product.getUnitPrice();
        this.image = product.getImage();
        this.categoryName =product.getCategory().getCategoryName();
    }
}
