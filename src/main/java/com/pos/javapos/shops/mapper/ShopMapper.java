package com.pos.javapos.shops.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.mapper.UserMapper;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.dto.ShopResponseDto;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShopMapper {

    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ShopMapper(ObjectMapper objectMapper, UserMapper userMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Shop fromShopDto(ShopDto shopDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDto, shop,"id");
        return shop;
    }

    public ShopDto fromShopToDto(Shop shop) throws JsonProcessingException {
        ShopDto shopDto = new ShopDto();
        BeanUtils.copyProperties(shop, shopDto);
        Map<String, Object> shopObject = objectMapper.readValue(shop.getShop_object(),Map.class);
        shopDto.setShop_object(shopObject);
        return shopDto;
    }

    public Shop fromShopRequestDto(ShopRequestDto shopRequestDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopRequestDto, shop);
        return shop;
    }

    public Shop fromShopResponseDtoToShop(ShopDto shopDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDto, shop);
        return shop;
    }

    public ShopResponseDto fromShopToResponseDto(Shop shop) throws JsonProcessingException {
        ShopResponseDto shopResponseDto = new ShopResponseDto();
        BeanUtils.copyProperties(shop, shopResponseDto);
        Map<String, Object> shopObject = objectMapper.readValue(shop.getShop_object(),Map.class);
        shopResponseDto.setShop_object(shopObject);
        UserDto owner = findUser(shop.getOwner());
        UserDto createdBy = findUser(shop.getCreatedBy());
        shopResponseDto.setOwner(owner);
        shopResponseDto.setCreatedBy(createdBy);
        return shopResponseDto;
    }

    private UserDto findUser(String userId){
        User user =userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.fromUserToDto(user);
    }
}
