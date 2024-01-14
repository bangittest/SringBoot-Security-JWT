package com.ra.service.role;

import com.ra.model.Role;

import java.util.List;

public interface RoleService {
    Role findByRoleName(String roleName);
    List<Role>findAll();
}
