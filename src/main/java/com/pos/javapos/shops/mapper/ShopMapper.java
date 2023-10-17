package com.pos.javapos.shops.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShopMapper {

    private final ObjectMapper objectMapper;

    public ShopMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
}
