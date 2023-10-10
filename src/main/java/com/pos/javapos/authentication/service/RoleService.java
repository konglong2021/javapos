package com.pos.javapos.authentication.service;

import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(String name);
    Boolean addPermissionToRole(String roleName,String permissionName);
    List<Role> findAll();
}
