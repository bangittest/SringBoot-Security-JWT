package com.ra.dto.respose.wishlist;

import com.ra.model.WishList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WishListResponseDTO {
    private Long id;
    private String productName;
    private String description;
    private Float unitPrice;
    private String image;
    private String categoryName;

    public WishListResponseDTO(WishList wishList) {
        this.id = wishList.getId();
        this.productName = wishList.getProduct().getProductName();
        this.description = wishList.getProduct().getDescription();
        this.unitPrice = wishList.getProduct().getUnitPrice();
        this.image = wishList.getProduct().getImage();
        this.categoryName = wishList.getProduct().getCategory().getCategoryName();
    }
}
