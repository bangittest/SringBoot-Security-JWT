package com.ra.service.user;

import com.ra.dto.request.UserRequestDTO;
import com.ra.dto.request.user.UserProfileDTO;
import com.ra.dto.request.user.UserRegisterRequestDTO;
import com.ra.dto.respose.UserResponseDTO;
import com.ra.dto.respose.user.UserDTO;
import com.ra.dto.respose.user.UserRegisterResponseDTO;
import com.ra.dto.respose.user.UserResponseDto;
import com.ra.exception.CustomException;
import com.ra.exception.RoleNotFoundExceptions;
import com.ra.exception.UserNotFoundException;
import com.ra.model.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {
    UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException;
    UserResponseDTO login(UserRequestDTO userRequestDTO) throws UserNotFoundException;
    User findById(Long id) throws UserNotFoundException;
    void updateUserRole(Long userId, Long roleId) throws  UserNotFoundException, RoleNotFoundExceptions;
    void removeUserRoles(Long userId,Long roleId) throws RoleNotFoundExceptions,UserNotFoundException;
    void lockUser(Long id) throws UserNotFoundException ;
    UserDTO checkById(Long id) throws UserNotFoundException;
    UserDTO updateProfile(Long userId,UserProfileDTO userProfileDTO)throws UserNotFoundException;
    void updatePassword(Long userId,String password) throws UserNotFoundException;
    List<UserResponseDto>findAllUser();
    User findByEmail(String email);
    User findByToken(String token);
    String generateResetToken(User user);
}
