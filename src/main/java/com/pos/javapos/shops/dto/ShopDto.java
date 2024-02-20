package com.pos.javapos.shops.dto;

import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.shops.entity.Branch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.NonNull;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ShopDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name ;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Contact is required")
    private String contact;
    private String email;
    private UserDto owner;
    private String logo;
    private String description;
    private Map<String,Object> shop_object;
    private List<BranchDto> branch;
    private Date createdAt;
    private Date updatedAt;
    private UserDto createdBy;

}
