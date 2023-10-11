package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.dto.UserRoleDto;
import com.pos.javapos.authentication.service.UserService;
import com.pos.javapos.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAuthority('user_access')")
    public ResponseEntity<?> addRoleToUser(@RequestBody UserRoleDto userRoleDto){
        try{
            UserDto userDto = userService.addRoleToUser(userRoleDto.getUser_id(),userRoleDto.getRole_id());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "Role has been to " + userDto.getUsername(), userDto)
            );
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
