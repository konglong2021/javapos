package com.pos.javapos.products.dto;

import com.pos.javapos.authentication.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private String image;
    private String sku;
    private String type;
    private Map<String, Object> product_object;
    private Date createdAt;
    private Date updatedAt;
    private UserDto createdBy;
}
