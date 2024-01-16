package com.ra.dto.respose.size;

import com.ra.model.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SizeResponseDTO {
    private String sizeName;

    public SizeResponseDTO(Size size) {
        this.sizeName = size.getName();
    }
}
