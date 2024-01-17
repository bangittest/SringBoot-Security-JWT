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
import com.ra.exception.UserRoleNotFoundExceptionss;
import com.ra.model.Role;
import com.ra.model.User;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.security.principle.UserDetailService;
import com.ra.security.principle.UserPrinciple;
import com.ra.service.role.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Override
    public UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException {
        // check trung
        if(userRepository.existsByUserName(userRegisterRequestDTO.getUserName())){
            throw new CustomException("UserName existed");
        }
        if(userRepository.existsByEmail(userRegisterRequestDTO.getEmail())){
            throw new CustomException("email existed");
        }
        User user=new User();
        //roles
        Set<Role>roles=new HashSet<>();
        if (user.getRoles()==null||user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        }
        else {
            user.getRoles().forEach(role -> {
                roles.add(roleService.findByRoleName(role.getName()));
            });
        }

        user.setUserName(userRegisterRequestDTO.getUserName());
        user.setEmail(userRegisterRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setStatus(true);
        user.setFullName(userRegisterRequestDTO.getFullName());
        user.setRoles(roles);
        user.setAddress(userRegisterRequestDTO.getAddress());
        user.setPhone(userRegisterRequestDTO.getPhone());
        userRepository.save(user);
        return new UserRegisterResponseDTO(user);
    }

@Override
public UserResponseDTO login(UserRequestDTO userRequestDTO) throws UserNotFoundException {
    Authentication authentication;
    try {
        authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(), userRequestDTO.getPassword()));
    } catch (AuthenticationException e) {
        throw new UserNotFoundException("Invalid username or password");
    }

    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

    return UserResponseDTO.builder()
            .token(jwtProvider.generateToken(userPrinciple))
//            .userName(userPrinciple.getUsername())
//            .roles(userPrinciple.getAuthorities()
//                    .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .build();
}



    @Override
    public User findById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void updateUserRole(Long userId, Long roleId,Long adminId) throws UserNotFoundException, RoleNotFoundExceptions {
        User userAdmin=userRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getId() == 1) {
            throw new UserNotFoundException("Role admin not found");
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundExceptions("Role not found"));
        if (role.getName().equalsIgnoreCase("ADMIN")){
            throw new RoleNotFoundExceptions("Role admin not found");
        }
        if (userAdmin.getId()==1){
            Set<Role> roles = user.getRoles();
            if (roles.contains(role)) {
                throw new RoleNotFoundExceptions("User da co have the specified role ay");
            }
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }else {
            throw new RoleNotFoundExceptions("Ban khong phai ADMIN k the phan quyen");
        }

    }

    @Override
    public void removeUserRoles(Long userId, Long roleId,Long adminId) throws RoleNotFoundExceptions, UserNotFoundException {
        User userAdmin=userRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException("User not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundExceptions("Role not found"));
//        if (role.getName().contains("ADMIN")){
//            throw new RoleNotFoundExceptions("Ban khong phai Admin nen khong co quyen vao chuc nang nay");
//        }
        if ("USER".equalsIgnoreCase(role.getName())) {
            throw new RoleNotFoundExceptions("USER mac dinh k the xoa");
        }
        if (user.getId().equals(userAdmin.getId())){
            throw new UserNotFoundException("Admin đang đăng nhập không thể xóa quyền");
        }
        if (userAdmin.getId()==1) {
            Set<Role> roles = user.getRoles();
            if (roles != null) {
                if (!roles.contains(role)) {
                    throw new RoleNotFoundExceptions("User chua co role nay");
                }
                roles.remove(role);
            }
            userRepository.save(user);
        }else {
            throw new RoleNotFoundExceptions("Ban khong phai ADMIN k the phan quyen");
        }
    }

    @Override
    public void lockUser(Long userId,Long adminId) throws UserNotFoundException {
        User userAdmin=userRepository.findById(adminId).orElseThrow(()->new UserNotFoundException("User not found " + adminId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found " +userId));
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            throw new UserNotFoundException("Admin users cannot be locked " +userId);
        }
        if (user.getId().equals(userAdmin.getId())){
            throw new UserNotFoundException("Admin đang đăng nhập không thể khóa");
        }
        user.setStatus(!user.getStatus());
        userRepository.save(user);
    }


    @Override
    public UserDTO checkById(Long id) throws UserNotFoundException {
        UserDTO userDTO=userRepository.findById(id).map(UserDTO::new).orElseThrow((() -> new UserNotFoundException("ban chua dang nhap")));
        return userDTO;
    }

    @Override
    public UserDTO updateProfile(Long userId,UserProfileDTO userProfileDTO) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("userId not found"));
        user.setId(userId);
        user.setAddress(userProfileDTO.getAddress());
        user.setFullName(userProfileDTO.getFullName());
        user.setPhone(userProfileDTO.getPhone());
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    @Override
    public void updatePassword(Long userId,String password) throws UserNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user not found"));
        if(user==null){
            throw new UserNotFoundException("user not found");
        }
//        user.setPassword(passwordEncoder.encode(password));
//        user.setId(userId);
        userRepository.updateUserPassword(userId,passwordEncoder.encode(password));
    }

    @Override
    public List<UserResponseDto> findAllUser() {
        List<User>list=userRepository.findAll();
        return list.stream().map((UserResponseDto::new)).toList();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User findByToken(String token){
        return userRepository.findByToken(token);
    }

    @Override
    public String generateResetToken(User user) {
        String resetToken = UUID.randomUUID().toString();
        user.setToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        return resetToken;
    }
}
