package com.pos.javapos.authentication.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.dto.UserResponseDto;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.shops.mapper.ShopMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public SignupDto fromUser(User user) {
        SignupDto signupDto = new SignupDto();
        BeanUtils.copyProperties(user, signupDto);
        return signupDto;
    }

    public User fromSignupDto(SignupDto signupDto) {
        User user = new User();
        BeanUtils.copyProperties(signupDto, user);
        return user;
    }

    public UserDto fromUserToDto(User user) {
       UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public User fromDtoToUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public UserResponseDto fromUserToResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setRoles(user.getRoles().stream().toList());
        userResponseDto.setShop(user.getShop().getName());
        userResponseDto.setBranch(user.getBranch().getName());
        return userResponseDto;
    }

}
