package com.pos.javapos.authentication.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopResponseDto;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import com.pos.javapos.shops.mapper.ShopMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String shop;
    private String branch;
    private List<Role> roles;
}
