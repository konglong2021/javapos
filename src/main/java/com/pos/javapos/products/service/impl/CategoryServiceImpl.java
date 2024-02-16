package com.pos.javapos.products.service.impl;

import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.entity.Category;
import com.pos.javapos.products.mapper.CategoryMapper;
import com.pos.javapos.products.repository.CategoryRepository;
import com.pos.javapos.products.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(category -> new CategoryDto()).toList();
    }

    @Override
    public CategoryDto show(Long id) {
        return categoryRepository.findById(id).map(category -> new CategoryDto()).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.fromDtoToCategory(categoryDto);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        category.setTag(categoryDto.getTag());
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public void delete(Long id) {

    }
}
