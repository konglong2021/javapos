package com.pos.javapos.authentication.controller;


import com.pos.javapos.helper.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    private MessageSource messageSource;



    @GetMapping
    public String greeting(HttpServletRequest request) {
        Locale locale = Locale.forLanguageTag(request.getHeader("Accept-Language"));

        return messageSource.getMessage("hello",null, locale );
    }
    @GetMapping("/hello")
    public ResponseEntity<?> hello(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(true,messageSource.getMessage("message",null, locale ), locale)
        );
    }
}
