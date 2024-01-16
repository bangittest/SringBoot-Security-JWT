package com.ra.dto.respose.product;

import com.ra.dto.respose.color.ColorResponseDTO;
import com.ra.dto.respose.size.SizeResponseDTO;
import com.ra.model.Product;
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
public class ProductResponseDto {
    private Long id;
    private String productName;
    private String sku;
    private String description;
    private Float unitPrice;
    private String image;
    private String categoryName;
    private Boolean status;
    private Set<ColorResponseDTO>colorResponseDTOS;
    private Set<SizeResponseDTO>sizeResponseDTOS;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.sku=product.getSku();
        this.description = product.getDescription();
        this.unitPrice = product.getUnitPrice();
        this.image = product.getImage();
        this.categoryName =product.getCategory().getCategoryName();
        this.status=product.getStatus();
        this.colorResponseDTOS=product.getColors().stream().map(ColorResponseDTO::new).collect(Collectors.toSet());
        this.sizeResponseDTOS=product.getSizes().stream().map(SizeResponseDTO::new).collect(Collectors.toSet());
    }
}
