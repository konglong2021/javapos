package com.pos.javapos.products.service.impl;

import com.pos.javapos.helper.FiltersSpecification;
import com.pos.javapos.helper.dto.RequestDto;
import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.entity.Category;
import com.pos.javapos.products.entity.Product;
import com.pos.javapos.products.mapper.CategoryMapper;
import com.pos.javapos.products.repository.CategoryRepository;
import com.pos.javapos.products.repository.ProductRepository;
import com.pos.javapos.products.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FiltersSpecification filtersSpecification;

    @Override
    public Page<CategoryDto> findAll(int page,int size,String sortOrder,String sortBy) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page,size,Sort.by(direction, "id"));
        Page<Category> categories = categoryRepository.findAll(pageRequest);
        return new PageImpl<>(categories.getContent().stream().map(category -> categoryMapper
                .fromCategoryToDto(category))
                .collect(Collectors.toList())
                ,pageRequest,categories.getTotalElements());
    }



    @Override
    public CategoryDto show(Long id) {
        return categoryRepository.findById(id).map(category -> new CategoryDto()).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.fromDtoToCategory(categoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.fromCategoryToDto(category);
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
    public Boolean delete(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
            if (category.getProducts().size() > 0) {
                throw new RuntimeException("Category has products");
            }
            categoryRepository.delete(category);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Boolean assignCategoryToProduct(Long productId, Long categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
            category.getProducts().add(product);
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    @Cacheable("categories")
    public Page<CategoryDto> getAll(int page, int size, RequestDto requestDto) {
        Sort.Direction sort = Sort.Direction.DESC;
        String sortByColumn = "id";
        PageRequest pageRequest = PageRequest.of(page,size,sort,sortByColumn);
        Specification<Category> specification = filtersSpecification.getSearchSpecificationList(requestDto.getSearchRequestDto());
        Page<Category> categories = categoryRepository.findAll(specification,pageRequest);
        return new PageImpl<>(categories.getContent().stream().map(category -> categoryMapper
                .fromCategoryToDto(category))
                .collect(Collectors.toList())
                ,pageRequest,categories.getTotalElements());
    }


}
