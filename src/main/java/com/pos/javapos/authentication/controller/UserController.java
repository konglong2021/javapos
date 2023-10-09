package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.dto.UserRoleDto;
import com.pos.javapos.authentication.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(@RequestBody UserRoleDto userRoleDto){
        try{
            userService.addRoleToUser(userRoleDto.getUser_id(),userRoleDto.getRole_name());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
