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
            @PathVariable String userId,
            @PathVariable String roleId) throws UserNotFoundException, RoleNotFoundExceptions {
        try {
            Long idUser= Long.valueOf(userId);
            Long idRole= Long.valueOf(roleId);
            userService.updateUserRole(idUser, idRole);
            return ResponseEntity.ok("Role updated successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("users/{userId}/role/{roleId}")
    public ResponseEntity<?> deleteUserRole(
            @PathVariable String userId,
            @PathVariable String roleId) throws UserNotFoundException, RoleNotFoundExceptions {
        try {
            Long idUser= Long.valueOf(userId);
            Long idRole= Long.valueOf(roleId);
            userService.removeUserRoles(idUser, idRole);
            return ResponseEntity.ok("Role deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) throws UserNotFoundException {
        try {
            Long id= Long.valueOf(userId);
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("users/{userId}")
    public ResponseEntity<?> lockUser(@PathVariable String userId) throws UserNotFoundException {
        try {
            Long id= Long.valueOf(userId);
            userService.lockUser(id);
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
