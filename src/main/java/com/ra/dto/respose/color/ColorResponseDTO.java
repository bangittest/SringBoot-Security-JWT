package com.ra.dto.respose.color;

import com.ra.model.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorResponseDTO {
    private String colorName;

    public ColorResponseDTO(Color color) {
        this.colorName = color.getName();
    }
}
