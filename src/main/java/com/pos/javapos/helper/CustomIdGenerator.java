package com.pos.javapos.helper;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import static com.pos.javapos.helper.Helper.PRODUCT_PREFIX;

public class CustomIdGenerator {

    private static String PREFIX = PRODUCT_PREFIX;
    private static AtomicLong counter;

    public CustomIdGenerator(String lastId,String prefix) {
        PREFIX = prefix;
        Long longLastId = getLastIdFromDatabase(lastId);
        counter = new AtomicLong(longLastId);
    }

    public String generateId() {
        return PREFIX + counter.incrementAndGet();
    }

    private Long getLastIdFromDatabase(String lastId) {
        if (lastId != null) {
            String previousId = lastId.substring(PREFIX.length(),(PREFIX.length() + 8));
            if (previousId.equals(getLastDateTime())) {
                return Long.parseLong(lastId.substring(PREFIX.length()));
            }
        }

        return Long.parseLong(getLastDateTime() + "0000");
    }

    private String getLastDateTime(){
        LocalDateTime now = LocalDateTime.now();
        String format = "yyyyMMdd";
        return now.format(DateTimeFormatter.ofPattern(format));
    }

}
