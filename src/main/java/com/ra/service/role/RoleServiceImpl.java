package com.ra.service.role;

import com.ra.model.Role;
import com.ra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
