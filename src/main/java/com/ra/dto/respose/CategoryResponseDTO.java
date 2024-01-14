package com.ra.dto.respose;

import com.ra.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String categoryName;
    private Boolean status;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
    }
}
