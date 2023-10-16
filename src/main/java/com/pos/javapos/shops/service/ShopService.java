package com.pos.javapos.shops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.entity.Shop;

import java.util.List;

public interface ShopService {

    Shop addShop(ShopDto shopDto) throws JsonProcessingException;
    Shop updateShop(ShopDto shopDto) throws JsonProcessingException;

    List<Shop> getShopsByUserId(Long userId);

    Shop getShopById(Long shopId);

    Boolean existedShop(Long shopId);
}
