package com.pos.javapos.authentication.dto;

import com.pos.javapos.authentication.entity.Role;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private List<Role> roles;
}
