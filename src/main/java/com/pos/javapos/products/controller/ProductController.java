package com.pos.javapos.products.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.products.dto.ProductDto;
import com.pos.javapos.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableCaching
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page",defaultValue = "0") int page,
                                        @RequestParam(name = "size",defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Fetched all Products",productService.findAll(page,size))
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto productDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true,"Created Product",productService.create(productDto))
        );
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> update(@PathVariable Long shopId,
                                    @RequestBody ProductDto productDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Updated Product",productService.update(shopId,productDto))
        );
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> delete(@PathVariable Long shopId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Deleted Product",productService.delete(shopId))
        );
    }
}
