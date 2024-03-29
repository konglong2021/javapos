package com.pos.javapos.authentication.service;

import com.pos.javapos.authentication.entity.Permission;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Slf4j
public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        });
        return authorities;

    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getId() {
        return user.getId().toString();
    }

    public Role getRole(){
        return user.getRoles().iterator().next();
    }

    public Shop getShop(){return user.getShop();}
    public Branch getBranch(){return user.getBranches();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
