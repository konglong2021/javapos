package com.pos.javapos.authentication.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private  Long id;
    private String name;
    private List<PermissionDto> permissions;
}
