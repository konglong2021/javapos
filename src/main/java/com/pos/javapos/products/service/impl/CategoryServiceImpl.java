package com.pos.javapos.products.service.impl;

import com.pos.javapos.helper.FiltersSpecification;
import com.pos.javapos.helper.RequestDto;
import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.entity.Category;
import com.pos.javapos.products.entity.Product;
import com.pos.javapos.products.mapper.CategoryMapper;
import com.pos.javapos.products.repository.CategoryRepository;
import com.pos.javapos.products.repository.ProductRepository;
import com.pos.javapos.products.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignCategoryToProduct(Long productId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        category.getProducts().add(product);
        categoryRepository.save(category);
    }

    @Override
    public Page<CategoryDto> getAll(int page, int size, RequestDto requestDto) {
        PageRequest pageRequest = PageRequest.of(page,size);
//        Specification<Category> specification = filtersSpecification.getSearchSpecification(requestDto.getSearchRequestDto());
        Specification<Category> specification = filtersSpecification.getSearchSpecificationList(requestDto.getSearchRequestDto());
        Page<Category> categories = categoryRepository.findAll(specification,pageRequest);
        return new PageImpl<>(categories.getContent().stream().map(category -> categoryMapper
                .fromCategoryToDto(category))
                .collect(Collectors.toList())
                ,pageRequest,categories.getTotalElements());
    }


}
