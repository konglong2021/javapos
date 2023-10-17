package com.pos.javapos.shops.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.mapper.UserMapper;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.entity.Shop;
import com.pos.javapos.shops.mapper.ShopMapper;
import com.pos.javapos.shops.repository.ShopRepository;
import com.pos.javapos.shops.service.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    public ShopServiceImpl(UserRepository userRepository, ShopRepository shopRepository, ShopMapper shopMapper, ObjectMapper objectMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.objectMapper = objectMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Shop addShop(ShopDto shopDto) throws JsonProcessingException {
        Shop shop = shopMapper.fromShopDto(shopDto);
        shop.setShop_object(objectMapper.writeValueAsString(shopDto.getShop_object()));
        return shopRepository.save(shop);
    }

    @Override
        public Shop updateShop(ShopDto shopDto) throws JsonProcessingException {
        Shop shop = shopMapper.fromShopDto(shopDto);
        shop.setShop_object(objectMapper.writeValueAsString(shopDto.getShop_object()));
        return shopRepository.save(shop);
    }

    @Override
    public List<ShopDto> getShopsByUserId(Long userId)  {
        List<Shop> shops = shopRepository.getShopsByUserId(userId);
       return shops.stream().map(shop ->
        {
            try {
                return getShopDto(shop);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        ).toList();
    }

    @Override
    public ShopDto getShopById(Long shopId) throws JsonProcessingException {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        return getShopDto(shop);
    }

    private ShopDto getShopDto(Shop shop) throws JsonProcessingException {
        ShopDto shopDto = shopMapper.fromShopToDto(shop);
        User creator = userRepository.findById(Long.parseLong(shop.getCreatedBy())).orElseThrow();
        if (shop.getOwner() != null){
            User owner = userRepository.findById(Long.parseLong(shop.getOwner())).orElse(null);
            shopDto.setOwner(userMapper.fromUserToDto(owner));
        }
        UserDto userDto = userMapper.fromUserToDto(creator);
        shopDto.setCreatedBy(userDto);
        return shopDto;
    }

    @Override
    public Boolean existedShop(Long shopId) {
        return shopRepository.existsById(shopId);
    }


}
