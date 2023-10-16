package com.pos.javapos.shops.mapper;

import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ShopMapper {
    public Shop fromShopDto(ShopDto shopDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDto, shop,"id");
        return shop;
    }

    public ShopDto fromShopToDto(Shop shop) {
        ShopDto shopDto = new ShopDto();
        BeanUtils.copyProperties(shop, shopDto);
        return shopDto;
    }
}
