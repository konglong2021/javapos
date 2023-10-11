package com.pos.javapos.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.User;

public interface UserService {
    UserDetailsImpl createUser(SignupDto signupDto) throws JsonProcessingException;
    UserDto addRoleToUser(Long user_id,Long role_id);
    Boolean removeRoleFromUser(Long user_id,Long role_id);
    UserDto getUserByUsername(String username);
}
