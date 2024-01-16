package com.ra.dto.respose.home;

import com.ra.dto.respose.color.ColorResponseDTO;
import com.ra.dto.respose.size.SizeResponseDTO;
import com.ra.model.Product;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDesDTO {
    private Long id;
    private String productName;
    private String description;
    private Float unitPrice;
    private String image;
    private String categoryName;
    private Set<ColorResponseDTO>colorResponseDTOS;
    private Set<SizeResponseDTO>sizeResponseDTOS;

    public ProductResponseDesDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.unitPrice = product.getUnitPrice();
        this.image = product.getImage();
        this.categoryName =product.getCategory().getCategoryName();
        this.colorResponseDTOS=product.getColors().stream().map(ColorResponseDTO::new).collect(Collectors.toSet());
        this.sizeResponseDTOS=product.getSizes().stream().map(SizeResponseDTO::new).collect(Collectors.toSet());
    }
}
