package com.pos.javapos.authentication.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.mapper.UserMapper;
import com.pos.javapos.authentication.repository.RoleRepository;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import com.pos.javapos.authentication.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsImpl createUser(SignupDto signupDto) throws JsonProcessingException {
        if (userRepository.existsByUsername(signupDto.getUsername())) {
           throw new RuntimeException("Username already exists");
        }
        User user = userMapper.fromSignupDto(signupDto);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setUser_object(objectMapper.writeValueAsString(signupDto.getUser_object()));
        return new UserDetailsImpl(userRepository.save(user));
    }

    public UserDto addRoleToUser(Long user_id,Long role_id) {
        try {
            User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));
            Role role = roleRepository.findById(role_id).orElseThrow(() -> new RuntimeException("Role not found"));
            user.assignRoleToUser(role);
            return userMapper.fromUserToDto(user);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean removeRoleFromUser(Long user_id, Long role_id) {
        try {
            User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));
            Role role = roleRepository.findById(role_id).orElseThrow(() -> new RuntimeException("Role not found"));
            user.removeRoleFromUser(role);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return null;
    }
}
