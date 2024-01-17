package com.ra.dto.respose.category;

import com.ra.model.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponseDto {
    private Long id;
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.id=category.getId();
        this.categoryName = category.getCategoryName();
    }
}
