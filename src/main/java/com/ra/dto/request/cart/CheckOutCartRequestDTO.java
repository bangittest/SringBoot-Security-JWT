package com.ra.dto.request.cart;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CheckOutCartRequestDTO {
    @NotEmpty(message = "orderId không được bỏ trống")
    private Set<String>orderId;
}
