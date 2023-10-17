package com.pos.javapos.shops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable Long shopId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true, "Shop fetched successfully", shopService.getShopById(shopId))
        );
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addShop(@Valid @RequestBody ShopDto shopDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(true, "Shop added successfully", shopService.addShop(shopDto))
        );
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopId, @RequestBody ShopDto shopDto) throws JsonProcessingException {
        boolean existedShop = shopService.existedShop(shopId);
        if (existedShop){
            return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true, "Shop updated successfully", shopService.updateShop(shopDto))
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(false, "Shop updated failed", null)
            );
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getShopByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Fetched all shop by user id",shopService.getShopsByUserId(userId))
        );
    }
}
