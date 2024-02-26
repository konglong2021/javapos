package com.pos.javapos.helper;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    private List<SearchRequestDto> searchRequestDto;
}
