package com.ra.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryRequestDTO {
    @NotEmpty(message = "Tên danh mục không được để trống")
    private String categoryName;
    private Boolean status=true;
}
