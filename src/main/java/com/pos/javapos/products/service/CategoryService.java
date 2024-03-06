package com.pos.javapos.products.service;

import com.pos.javapos.helper.dto.RequestDto;
import com.pos.javapos.products.dto.CategoryDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryDto> findAll(int page,int size,String sortOrder,String sortBy);
    CategoryDto show(Long id);
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(Long id,CategoryDto categoryDto);
    Boolean delete(Long id);

    Boolean assignCategoryToProduct(Long productId, Long categoryId);

    Page<CategoryDto> getAll(int page, int size, RequestDto requestDto);

}
