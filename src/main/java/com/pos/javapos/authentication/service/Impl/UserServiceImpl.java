package com.pos.javapos.authentication.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.mapper.UserMapper;
import com.pos.javapos.authentication.repository.RoleRepository;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import com.pos.javapos.authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

    public void addRoleToUser(Long user_id,String role_name){
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(role_name).orElseThrow(() -> new RuntimeException("Role not found"));
        log.info("Adding role {} to user {}", role_name, user.getUsername());
        user.assignRoleToUser(role);
//        role.getUsers().add(user);
    }
}
