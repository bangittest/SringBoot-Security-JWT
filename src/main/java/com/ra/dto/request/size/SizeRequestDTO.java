package com.ra.dto.request.size;

import com.ra.model.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SizeRequestDTO {
    @NotNull(message = "ten size khong duoc bo trong")
    private String name;

}
