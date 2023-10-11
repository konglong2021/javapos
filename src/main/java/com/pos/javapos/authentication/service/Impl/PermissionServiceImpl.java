package com.pos.javapos.authentication.service.Impl;

import com.pos.javapos.authentication.dto.PermissionDto;
import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.repository.PermissionRepository;
import com.pos.javapos.authentication.service.PermissionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PermissionDto> findAll() {
        List<Permission> permissions = permissionRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return permissions.stream().map(permission -> {
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setId(permission.getId());
            permissionDto.setName(permission.getName());
            return permissionDto;
        }).toList();
    }
}
