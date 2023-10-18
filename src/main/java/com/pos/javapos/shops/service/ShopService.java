package com.pos.javapos.shops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.dto.ShopRequestDto;
import com.pos.javapos.shops.entity.Shop;

import java.util.List;

public interface ShopService {

    Shop addShop(ShopRequestDto shopRequestDto) throws JsonProcessingException;
    Shop updateShop(ShopRequestDto shopRequestDto) throws JsonProcessingException;

    List<ShopDto> getShopsByUserId(Long userId);

    ShopDto getShopById(Long shopId) throws JsonProcessingException;

    Boolean existedShop(Long shopId);

    Boolean assignShopToUser(Long userId, Long shopId);
}
