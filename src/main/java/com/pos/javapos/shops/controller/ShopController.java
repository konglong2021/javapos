package com.pos.javapos.shops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<ApiResponse> addShop(@Valid @RequestBody ShopRequestDto shopRequestDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(true, "Shop added successfully", shopService.addShop(shopRequestDto))
        );
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopId, @RequestBody ShopRequestDto shopRequestDto) throws JsonProcessingException {
        boolean existedShop = shopService.existedShop(shopId);
        if (existedShop){
            shopRequestDto.setId(shopId);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true, "Shop updated successfully", shopService.updateShop(shopRequestDto))
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(false, "Shop updated failed", null)
            );
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('user_access')")
    public ResponseEntity<ApiResponse> getShopByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Fetched all shop by user id",shopService.getShopsByUserId(userId))
        );
    }

    @GetMapping("/get-users-by-shop-id/{shopId}")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_User')")
    public Page<UserDto> getUsersByShopId(@PathVariable Long shopId,
                                          @RequestParam(name = "page",defaultValue = "0") int page,
                                          @RequestParam(name = "size",defaultValue = "10") int size){
        return shopService.fetchUsersByShopId(shopId, page,size);

    }

//    @GetMapping("/assign-shop-to-user/{userId}/{shopId}")
//    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_User')")
//    public ResponseEntity<ApiResponse> assignShopToUser(@PathVariable Long userId, @PathVariable Long shopId){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ApiResponse(true,"Shop assigned successfully",shopService.assignShopToUser(userId,shopId))
//        );
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }
}
