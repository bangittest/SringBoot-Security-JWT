package com.ra.dto.request.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartRequestDTO {
    @NotNull(message = "product Id không được bỏ trống")
    private Long productId;

    @NotNull(message = "quantity không được bỏ trống")
    private int quantity;
}
