package com.pos.javapos.helper.dto;

import com.pos.javapos.helper.SearchRequestDto;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

@Data
public class RequestDto {
    private List<SearchRequestDto> searchRequestDto;

}
