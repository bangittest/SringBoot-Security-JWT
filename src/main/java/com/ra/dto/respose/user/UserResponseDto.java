package com.ra.dto.respose.user;

import com.ra.model.Role;
import com.ra.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private String fullName;
    private Boolean status;
    private String phone;
    private String address;
    Set<String>roles;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.status = user.getStatus();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }
}
