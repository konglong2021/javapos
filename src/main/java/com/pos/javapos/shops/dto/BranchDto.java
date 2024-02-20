package com.pos.javapos.shops.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class BranchDto {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String logo;
    private String description;
    private Map<String,Object> branch_object;
    private Long shop_id;
}
