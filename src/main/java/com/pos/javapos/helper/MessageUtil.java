package com.pos.javapos.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, Locale locale){
        return messageSource.getMessage(code, null,"No Translation Found !", locale);
    }
}
