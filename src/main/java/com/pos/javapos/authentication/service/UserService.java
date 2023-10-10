package com.pos.javapos.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.User;

public interface UserService {
    UserDetailsImpl createUser(SignupDto signupDto) throws JsonProcessingException;
    void addRoleToUser(Long user_id,String role_name);
    UserDto getUserByUsername(String username);
}
