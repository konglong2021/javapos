package com.pos.javapos.helper;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String column;
    private String value;
}
