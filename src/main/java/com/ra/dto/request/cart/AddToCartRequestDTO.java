package com.ra.dto.request.cart;

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
    @NotNull(message = "id product khong dc bo trong")
    private Long productId;
    private Integer quantity=1;
}
