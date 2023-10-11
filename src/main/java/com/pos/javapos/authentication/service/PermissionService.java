package com.pos.javapos.authentication.service;

import com.pos.javapos.authentication.dto.PermissionDto;
import com.pos.javapos.authentication.entity.Permission;

import java.util.List;

public interface PermissionService {
    Permission addPermission(String name);
    List<PermissionDto> findAll();
}
