package com.pos.javapos.authentication.service.Impl;

import com.pos.javapos.authentication.dto.PermissionDto;
import com.pos.javapos.authentication.dto.RoleDto;
import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.repository.PermissionRepository;
import com.pos.javapos.authentication.repository.RoleRepository;
import com.pos.javapos.authentication.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Boolean addPermissionToRole(Long role_id, Long permission_id) {
        try {
            Role role = roleRepository.findById(role_id).orElseThrow(() -> new RuntimeException("Role not found"));
            Permission permission = permissionRepository.findById(permission_id).orElseThrow(() -> new RuntimeException("Permission not found"));
            if (role.hasPermission(permission)) {
                return false;
            }
            role.assignPermissionToRole(permission);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean removePermissionFromRole(Long role_id, Long permission_id) {
        try {
            Role role = roleRepository.findById(role_id).orElseThrow(() -> new RuntimeException("Role not found"));
            Permission permission = permissionRepository.findById(permission_id).orElseThrow(() -> new RuntimeException("Permission not found"));
            role.removePermissionFromRole(permission);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDto.setPermissions(role.getPermissions().stream().map(permission -> {
                PermissionDto permissionDto = new PermissionDto();
                permissionDto.setId(permission.getId());
                permissionDto.setName(permission.getName());
                return permissionDto;
            }).toList());
            return roleDto;
        }).toList();
    }


}
