package com.pos.javapos.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.LoginDto;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.dto.TokenDto;
import com.pos.javapos.authentication.security.TokenGenerator;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import com.pos.javapos.authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupDto signupDto) throws JsonProcessingException {
        UserDetailsImpl user = userService.createUser(signupDto);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user,signupDto.getPassword(), Collections.emptyList());
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
     Authentication authentication = daoAuthenticationProvider.authenticate(
             UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword())
     );
     return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody TokenDto tokenDto){
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDto.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }


}
