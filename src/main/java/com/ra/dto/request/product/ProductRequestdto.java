package com.ra.dto.request.product;

import com.ra.model.Color;
import com.ra.model.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequestdto {
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String productName;
    @NotEmpty(message = "Mô tả sản phẩm không được để trống")
    private String description;
    @NotNull(message = "Tiền sản phẩm không được bỏ trống")
    @Positive(message = "Tiền sản phẩm phải lớn hơn 0")
    private Float unitPrice;
//    private MultipartFile image;
    @NotNull(message = "Danh mục không được bỏ trống")
    private Long categoryId;
    private Set<String>colors;
    private Set<String>sizes;
}
