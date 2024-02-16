package com.pos.javapos.authentication.dto;

import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class CurrentUserDto {
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> roles;
    private Shop shop;
    private Branch branch;
}
