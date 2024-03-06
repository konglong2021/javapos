package com.pos.javapos.products.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
    private String tag;
}
