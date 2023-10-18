package com.pos.javapos.shops.dto;

import com.pos.javapos.authentication.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ShopRequestDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name ;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Contact is required")
    private String contact;
    private String email;
    private String owner;

    private String logo;
    private String description;
    private Map<String,Object> shop_object;
    private Date createdAt;
    private Date updatedAt;
    private UserDto createdBy;
}
