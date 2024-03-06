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

import static com.pos.javapos.helper.Helper.PAGE_NUMBER;
import static com.pos.javapos.helper.Helper.PAGE_SIZE;

@RestController
@EnableCaching
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page",defaultValue = PAGE_NUMBER) int page,
                                        @RequestParam(name = "size",defaultValue = PAGE_SIZE) int size) {
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
        Boolean isDelete = productService.delete(shopId);
        return ResponseEntity.status(isDelete ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(
                new ApiResponse(isDelete,"Deleted Product",null)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> findProductsByCategory(@PathVariable Long categoryId,
                                                    @RequestParam(name = "page",defaultValue = PAGE_NUMBER) int page,
                                                    @RequestParam(name = "size",defaultValue = PAGE_SIZE) int size){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Fetched Products",productService.findProductsByCategory(categoryId,page,size))
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<?> findProductsByBranch(@PathVariable Long branchId,
                                                    @RequestParam(name = "page",defaultValue = PAGE_NUMBER) int page,
                                                    @RequestParam(name = "size",defaultValue = PAGE_SIZE) int size){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,"Fetched Products",productService.findProductsByBranch(branchId,page,size))
        );
    }

    @GetMapping("/{categoryId}/{productId}")
    public ResponseEntity<?> assignProductToCategory(@PathVariable Long categoryId,
                                                        @PathVariable Long productId){
        Boolean isAssigned = productService.assignProductToCategory(productId,categoryId);
        return ResponseEntity.status(isAssigned ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(
                new ApiResponse(isAssigned,"category_productId_categoryId",null)
        );
    }
}
