package com.ra.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequestDTO {
    @NotEmpty(message = "Tài khoản không được để trống")
    private String userName;
    @Size(min = 3,message = "Nhập mật khẩu ít nhất 6 ký tự")
    private String password;
}
