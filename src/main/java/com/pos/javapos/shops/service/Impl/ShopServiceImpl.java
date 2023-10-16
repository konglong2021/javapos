package com.pos.javapos.shops.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.shops.dto.ShopDto;
import com.pos.javapos.shops.entity.Shop;
import com.pos.javapos.shops.mapper.ShopMapper;
import com.pos.javapos.shops.repository.ShopRepository;
import com.pos.javapos.shops.service.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ObjectMapper objectMapper;

    public ShopServiceImpl(ShopRepository shopRepository, ShopMapper shopMapper, ObjectMapper objectMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.objectMapper = objectMapper;
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
    public List<Shop> getShopsByUserId(Long userId) {
        return shopRepository.getShopsByUserId(userId);
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
    }

    @Override
    public Boolean existedShop(Long shopId) {
        return shopRepository.existsById(shopId);
    }


}
