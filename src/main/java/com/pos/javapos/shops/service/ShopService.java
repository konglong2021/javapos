package com.pos.javapos.shops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.dto.ShopResponseDto;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopService {

    ShopResponseDto addShop(ShopRequestDto shopRequestDto) throws JsonProcessingException;
    ShopResponseDto updateShop(ShopRequestDto shopRequestDto) throws JsonProcessingException;

    List<ShopDto> getShopsByUserId(Long userId);

    ShopDto getShopById(Long shopId) throws JsonProcessingException;

    Boolean existedShop(Long shopId);

//    Boolean assignShopToUser(Long userId, Long shopId);

    Page<UserDto> fetchUsersByShopId(Long shopId, int page,int size);

}
