package com.ra.dto.request.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WishListRequestDTO {
    @NotNull(message = "id product khong dc bo trong")
    private Long productId;
}
