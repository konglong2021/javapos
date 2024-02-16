package com.pos.javapos.products.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.products.dto.ProductDto;
import com.pos.javapos.products.entity.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    private final ObjectMapper objectMapper;
    public ProductMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public  ProductDto fromProductToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public Product fromDtoToProduct(ProductDto productDto) throws JsonProcessingException {
        Product product = new Product();
//        BeanUtils.copyProperties(productDto, product);
        product.setImage(productDto.getImage());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setType(productDto.getType());
        product.setProduct_object(objectMapper.writeValueAsString(productDto.getProduct_object()));
        return product;
    }
}
