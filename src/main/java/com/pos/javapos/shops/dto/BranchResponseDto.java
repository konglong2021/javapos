package com.pos.javapos.shops.dto;

import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.shops.entity.Shop;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class BranchResponseDto {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String logo;
    private String description;
    private Map<String,Object> branch_object;
    private ShopResponseDto shop;
    private UserDto createdBy;
    private UserDto updatedBy;
    private Date createdAt;
    private Date updatedAt;

}
