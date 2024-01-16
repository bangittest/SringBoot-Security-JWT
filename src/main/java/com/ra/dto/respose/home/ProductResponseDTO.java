package com.ra.dto.respose.home;

import com.ra.dto.respose.color.ColorResponseDTO;
import com.ra.dto.respose.size.SizeResponseDTO;
import com.ra.model.Category;
import com.ra.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private Float unitPrice;
    private String image;
    private String categoryName;
    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.unitPrice = product.getUnitPrice();
        this.image = product.getImage();
        this.categoryName =product.getCategory().getCategoryName();
    }
}
