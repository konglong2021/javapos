package com.pos.javapos.products.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.products.dto.ProductDto;
import com.pos.javapos.products.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductDto> findAll(int page, int size);
    ProductDto show(Long id);
    ProductDto create(ProductDto productDto) throws JsonProcessingException;
    ProductDto update(Long id,ProductDto productDto) throws JsonProcessingException;
    Boolean delete(Long id);
    Boolean assignProductToCategory(Long categoryId, Long productId);

    Page<ProductDto> findProductsByCategory(Long categoryId, int page, int size);
    Page<ProductDto> findProductsByBranch(Long branchId, int page, int size);
}
