package com.ra.controller.admin;

import com.ra.dto.respose.user.UserResponseDto;
import com.ra.exception.RoleNotFoundExceptions;
import com.ra.exception.UserNotFoundException;
import com.ra.model.Role;
import com.ra.model.User;
import com.ra.service.role.RoleService;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/users")
    public ResponseEntity<?>findAll(){
        List<UserResponseDto>list=userService.findAllUser();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/role/{roleId}")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) throws UserNotFoundException, RoleNotFoundExceptions {
            userService.updateUserRole(userId, roleId);
            return ResponseEntity.ok("Role updated successfully");
    }

    @DeleteMapping("users/{userId}/role/{roleId}")
    public ResponseEntity<?> deleteUserRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) throws UserNotFoundException, RoleNotFoundExceptions {
            userService.removeUserRoles(userId, roleId);
            return ResponseEntity.ok("Role deleted successfully");
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) throws UserNotFoundException {
        try {
            User user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("users/{userId}")
    public ResponseEntity<?> lockUser(@PathVariable Long userId) throws UserNotFoundException {
        try {
            userService.lockUser(userId);
            return ResponseEntity.ok("User locked successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?>getRolesAll(){
        List<Role>roleList=roleService.findAll();
        return ResponseEntity.ok(roleList);
    }

}
