package com.pos.javapos.authentication.service.Impl;

import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.repository.PermissionRepository;
import com.pos.javapos.authentication.service.PermissionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission addPermission(String name) {
        if (permissionRepository.existsByName(name)) {
            throw new RuntimeException("Permission already exists");
        }
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }
}
