package com.pos.javapos;


import com.pos.javapos.authentication.security.JwtToUserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaposApplicationTests {
    @Autowired
    private JwtToUserConverter jwtToUserConverter;

}
