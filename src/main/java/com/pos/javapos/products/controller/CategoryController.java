package com.pos.javapos.products.controller;

import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.helper.MessageUtil;
import com.pos.javapos.helper.dto.RequestDto;
import com.pos.javapos.products.dto.CategoryDto;
import com.pos.javapos.products.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static com.pos.javapos.helper.Helper.PAGE_NUMBER;
import static com.pos.javapos.helper.Helper.PAGE_SIZE;

@RestController
@EnableCaching
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    MessageUtil messageUtil;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(Locale locale,
                                     @RequestParam (name = "page",defaultValue = PAGE_NUMBER) int page,
                                     @RequestParam (name = "size",defaultValue = PAGE_SIZE) int size,
                                     @RequestParam(name = "sortBy",defaultValue = "id") String sortBy,
                                     @RequestParam(name = "sortOrder",defaultValue = "asc") String sortOrder){
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse(true,messageUtil.getMessage("category_fetch_all",locale),categoryService.findAll(page,size,sortOrder,sortBy))
        );
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAll(Locale locale,
                                              @RequestParam (name = "page",defaultValue = PAGE_NUMBER) int page,
                                              @RequestParam (name = "size",defaultValue = PAGE_SIZE) int size,
                                              @RequestBody RequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,messageUtil.getMessage("category_fetch_all",locale),categoryService.getAll(page,size,requestDto))
        );
    }

    @PostMapping
    public ResponseEntity<?> createCategory(Locale locale,
            @RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true,messageUtil.getMessage("category_create",locale),categoryService.create(categoryDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(Locale locale,
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,messageUtil.getMessage("category_update",locale),categoryService.update(id,categoryDto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(Locale locale,
            @PathVariable Long id){
        Boolean isDelete = categoryService.delete(id);
        return ResponseEntity.status(isDelete ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(
                new ApiResponse(isDelete,messageUtil.getMessage("category_delete",locale),null)
        );
    }

    @GetMapping("/{categoryId}/{productId}")
    public ResponseEntity<?> assignCategoryToProduct(@PathVariable Long categoryId,
                                                            @PathVariable Long productId){
        Boolean isAssigned = categoryService.assignCategoryToProduct(productId,categoryId);

        return ResponseEntity.status(isAssigned ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(
                new ApiResponse(isAssigned,"category_productId_categoryId",null)
        );
    }
}
