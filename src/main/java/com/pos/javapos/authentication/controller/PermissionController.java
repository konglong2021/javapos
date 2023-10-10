package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.dto.PermissionDto;
import com.pos.javapos.authentication.service.PermissionService;
import com.pos.javapos.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/addPermission")
    public ResponseEntity<?> addPermission(@RequestBody PermissionDto permissionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true, "Permission added", permissionService.addPermission(permissionDto.getName()))
        );
    }
}
