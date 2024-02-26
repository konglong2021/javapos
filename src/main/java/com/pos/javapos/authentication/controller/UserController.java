package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.dto.CurrentUserDto;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.dto.UserResponseDto;
import com.pos.javapos.authentication.dto.UserRoleDto;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.service.UserService;
import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.helper.CurrentUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addRoleToUser")
    @PreAuthorize("hasAuthority('user_access')")
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

    @GetMapping("/shop/{shopId}/branch/{branchId}")
    @PreAuthorize("hasAuthority('user_access')")
    public ResponseEntity<?> updateShopBranch(@PathVariable Long shopId,@PathVariable Long branchId){
        try{
            CurrentUserDto currentUserInfo = new CurrentUserInfo().getCurrentUser();
            UserResponseDto userDto  = userService.updateShopBranch(currentUserInfo.getId(),shopId,branchId);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "Shop has been updated", userDto)
            );
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
