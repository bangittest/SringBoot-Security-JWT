package com.ra.dto.respose.user;

import com.ra.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private String userName;
    private String fullName;
    private String phone;
    private String address;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.address = user.getAddress();
    }
}
