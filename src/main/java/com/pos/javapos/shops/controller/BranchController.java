package com.pos.javapos.shops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.shops.dto.BranchDto;
import com.pos.javapos.shops.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
    @Autowired
    BranchService branchService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addBranch(@RequestBody BranchDto branchDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(true, "Branch added successfully", branchService.addBranch(branchDto))
        );
    }

    @GetMapping("/list/{shopId}")
    public ResponseEntity<ApiResponse> listBranchesByShopId(@PathVariable Long shopId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true, "Branch fetched successfully", branchService.getBranchesByShopId(shopId))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) throws JsonProcessingException {
        branchDto.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true, "Branch updated successfully", branchService.updateBranch(branchDto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true, "Branch deleted successfully", null)
        );
    }
}
