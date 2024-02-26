package com.pos.javapos.products.service;

import com.pos.javapos.helper.RequestDto;
import com.pos.javapos.products.dto.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryDto> findAll(int page,int size,String sortOrder,String sortBy);
    CategoryDto show(Long id);
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(Long id,CategoryDto categoryDto);
    void delete(Long id);

    void assignCategoryToProduct(Long productId, Long categoryId);

    Page<CategoryDto> getAll(int page, int size, RequestDto requestDto);

}
