package com.pos.javapos.authentication.service;

import com.pos.javapos.authentication.dto.RoleDto;
import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(String name);
    Boolean addPermissionToRole(Long role_id,Long permission_id);
    Boolean removePermissionFromRole(Long role_id,Long permission_id);
    List<RoleDto> findAll();
}
