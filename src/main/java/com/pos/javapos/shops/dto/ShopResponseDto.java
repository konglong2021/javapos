package com.pos.javapos.shops.dto;

import com.pos.javapos.authentication.dto.UserDto;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ShopResponseDto {
    private Long id;
    private String name ;
    private String address;
    private String contact;
    private String email;
    private UserDto owner;

    private String logo;
    private String description;
    private Map<String,Object> shop_object;
    private Date createdAt;
    private Date updatedAt;
    private UserDto createdBy;

}
