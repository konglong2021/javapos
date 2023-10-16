package com.pos.javapos.shops.dto;

import lombok.Data;
import org.springframework.lang.NonNull;


import java.util.Map;

@Data
public class ShopDto {
    private Long id;

    private String name;

    private String address;

    private String contact;

    private String email;
    private String owner;

    private String logo;
    private String description;
    private Map<String,Object> shop_object;

}
