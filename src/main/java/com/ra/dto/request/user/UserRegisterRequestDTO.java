package com.ra.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterRequestDTO {
    @NotEmpty(message = "user không được bỏ trống")
    private String userName;
    @Email(message = "nhập đúng định dạng email")
    private String email;
    @NotEmpty(message = "password không được bỏ trống")
    private String password;
    @NotEmpty(message = "tên không được bỏ trống")
    private String fullName;
    @Pattern(regexp = "^(0?)(32|33|34|35|36|37|38|39|56|58|59|70|76|77|78|79|81|82|83|84|85|86|87|88|89|90|91|92|93|94|96|97|98|99)[0-9]{7}$")
    private String phone;
    @NotEmpty(message = "dia chi không được bỏ trống ")
    private String address;
}
