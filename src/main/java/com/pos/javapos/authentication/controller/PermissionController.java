package com.pos.javapos.authentication.controller;

import com.pos.javapos.authentication.service.PermissionService;
import com.pos.javapos.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/addPermission/{name}")
    public ResponseEntity<?> addPermission(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true, "Permission added", permissionService.addPermission(name))
        );
    }
}
