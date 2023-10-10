package com.pos.javapos.authentication.service.Impl;

import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.repository.PermissionRepository;
import com.pos.javapos.authentication.repository.RoleRepository;
import com.pos.javapos.authentication.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role addRole(String name) {
        if (roleRepository.existsByName(name)) {
            throw new RuntimeException("Role already exists");
        }
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Override
    public Boolean addPermissionToRole(String roleName,String permissionName) {
        try {
            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
            Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new RuntimeException("Permission not found"));
            role.assignPermissionToRole(permission);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }


}
