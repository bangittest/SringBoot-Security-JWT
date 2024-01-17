package com.ra.dto.respose.cart;

import com.ra.model.Cart;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartResponseDTO {
    private Long cartId;
    private Long productId;
    private String productName;
    private Float price;
    private Integer quantity;
    private String sizeName;
    private String colorName;

    public CartResponseDTO(Cart cart) {
        this.cartId = cart.getId();
        this.productId = cart.getProduct().getId();
        this.productName = cart.getProduct().getProductName();
        this.price = cart.getProduct().getUnitPrice();
        this.quantity = cart.getQuantity();
        this.sizeName = cart.getSize().getName();
        this.colorName=cart.getColor().getName();
    }
}
