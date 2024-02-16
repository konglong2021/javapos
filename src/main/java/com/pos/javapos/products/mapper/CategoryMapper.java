package com.pos.javapos.products.mapper;

import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.entity.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDto fromCategoryToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }

    public Category fromDtoToCategory(CategoryDto categoryDto){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        return category;
    }
}
