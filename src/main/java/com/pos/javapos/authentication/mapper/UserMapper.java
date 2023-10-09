package com.pos.javapos.authentication.mapper;

import com.pos.javapos.authentication.dto.SignupDto;
import com.pos.javapos.authentication.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public SignupDto fromUser(User user) {
        SignupDto signupDto = new SignupDto();
        BeanUtils.copyProperties(user, signupDto);
        //company.setCompany_object(objectMapper.writeValueAsString(companyDto.getCompany_object()));
        return signupDto;
    }

    public User fromSignupDto(SignupDto signupDto) {
        User user = new User();
        BeanUtils.copyProperties(signupDto, user);
        return user;
    }
}
