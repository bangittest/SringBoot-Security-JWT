package com.ra.dto.request.cart;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartRequestDTO {
    @NotEmpty(message = "id product khong dc bo trong")
    private String productId;
    @NotEmpty(message = "quantity product khong dc bo trong")
    private String quantity;
    @NotEmpty(message = "ten mau khong dc bo trong")
    private String color;
    @NotEmpty(message = "so size khong dc bo trong")
    private String size;
}
