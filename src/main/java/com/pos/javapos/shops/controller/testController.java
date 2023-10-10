package com.pos.javapos.shops.controller;


import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.repository.RoleRepository;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class testController {

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping
    public String test(){

        SecurityContext context = SecurityContextHolder.getContext();

        // Retrieve the Authentication object
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // You have an authenticated user

            // Retrieve the username
            String username = authentication.getName();
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            // Retrieve user authorities (roles)
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();
            Boolean isAdmin = authorities.stream()
                    .anyMatch(authority -> authority.getAuthority().equals("user_access"));

            log.info("authentication: {}", authentication.getAuthorities().toString());
            // You can access other user-related information as needed

            return "Hello " + username + " you have " + authorities.toString() + " permissions";

        } else {
            return "Hello guest";
        }

    }
}
