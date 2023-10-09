package com.pos.javapos.authentication.dto;

import lombok.Data;

@Data
public class UserRoleDto {
    private Long user_id;

    private String role_name;
}
