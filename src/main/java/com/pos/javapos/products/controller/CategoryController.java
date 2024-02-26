package com.pos.javapos.products.controller;

import com.pos.javapos.helper.ApiResponse;
import com.pos.javapos.helper.MessageUtil;
import com.pos.javapos.helper.RequestDto;
import com.pos.javapos.products.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static com.pos.javapos.helper.Helper.PAGE_NUMBER;
import static com.pos.javapos.helper.Helper.PAGE_SIZE;

@RestController
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
}
