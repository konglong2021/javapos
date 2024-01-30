package com.pos.javapos.shops.service.Impl;

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
import com.pos.javapos.shops.mapper.ShopMapper;
import com.pos.javapos.shops.repository.ShopRepository;
import com.pos.javapos.shops.service.ShopService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public ShopResponseDto addShop(ShopRequestDto shopRequestDto) throws JsonProcessingException {
        Shop shop = shopMapper.fromShopRequestDto(shopRequestDto);
        shop.setShop_object(objectMapper.writeValueAsString(shopRequestDto.getShop_object()));
        shop = shopRepository.save(shop);
        return shopMapper.fromShopToResponseDto(shop);
    }

    @Override
        public ShopResponseDto updateShop(ShopRequestDto shopRequestDto) throws JsonProcessingException {
        Shop shop = shopRepository.findById(shopRequestDto.getId()).orElseThrow(() -> new RuntimeException("Shop not found"));
        BeanUtils.copyProperties(shopRequestDto, shop);
        shop.setShop_object(objectMapper.writeValueAsString(shopRequestDto.getShop_object()));
        shop = shopRepository.save(shop);
        return shopMapper.fromShopToResponseDto(shop);
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
        User creator = userRepository.findById(Long.parseLong(shop.getCreatedBy())).orElse(null);
        if (shop.getOwner() != null){
            User owner = userRepository.findById(Long.parseLong(shop.getOwner())).orElse(null);
            shopDto.setOwner(userMapper.fromUserToDto(owner));
        }
        if (creator != null){
            UserDto userDto = userMapper.fromUserToDto(creator);
            shopDto.setCreatedBy(userDto);
        }

        return shopDto;
    }

    @Override
    public Boolean existedShop(Long shopId) {
        return shopRepository.existsById(shopId);
    }

//    @Override
//    public Boolean assignShopToUser(Long userId, Long shopId) {
//        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        try {
//            shop.assignUserToShop(user);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }

    @Override
    public Page<UserDto> fetchUsersByShopId(Long shopId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<User> users = userRepository.getUsersByShopId(shopId,pageRequest);

        return new PageImpl<>(users.getContent().stream().map(userMapper::fromUserToDto).collect(Collectors.toList()),pageRequest,users.getTotalElements());
    }


}
