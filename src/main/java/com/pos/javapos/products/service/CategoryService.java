package com.pos.javapos.products.service;

import com.pos.javapos.products.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto show(Long id);
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(Long id,CategoryDto categoryDto);
    void delete(Long id);
}
