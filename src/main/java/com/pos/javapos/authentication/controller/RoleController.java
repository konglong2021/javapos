package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.dto.RoleDto;
import com.pos.javapos.authentication.dto.addPermissionRoleDto;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.service.RoleService;
import com.pos.javapos.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/addRole")
     public Role addRole(@RequestBody RoleDto roleDto){
       return roleService.addRole(roleDto.getName());
     }

     @PostMapping("/addPermissionToRole")
     public ResponseEntity<?> addPermissionToRole(@RequestBody addPermissionRoleDto addPermissionRoleDto){
         Boolean addPermissionToRole = roleService.addPermissionToRole(addPermissionRoleDto.getRoleName(), addPermissionRoleDto.getPermissionName());
         return ResponseEntity.status(HttpStatus.CREATED).body(
                 new ApiResponse(addPermissionToRole, "Permission added to role", null)
         );
     }

     @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true, "Roles List", roleService.findAll())
        );
    }

}
