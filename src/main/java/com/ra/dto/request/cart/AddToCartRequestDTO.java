package com.ra.dto.request.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AddToCartRequestDTO {
    @NotEmpty(message = "id product khong dc bo trong")
    private String productId;
    private String quantity= String.valueOf(1);
    @NotEmpty(message = "ten mau khong dc bo trong")
    private String color;
    @NotEmpty(message = "so size khong dc bo trong")
    private String size;
}
