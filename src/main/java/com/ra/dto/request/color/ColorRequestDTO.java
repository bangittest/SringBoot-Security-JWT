package com.ra.dto.request.color;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorRequestDTO {
    @NotEmpty(message = "ten mau khong duoc bo trong")
    private String name;
}
