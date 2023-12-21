package com.pos.javapos.shops.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.javapos.authentication.dto.UserDto;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.authentication.mapper.UserMapper;
import com.pos.javapos.authentication.repository.UserRepository;
import com.pos.javapos.shops.dto.BranchDto;
import com.pos.javapos.shops.dto.BranchResponseDto;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import com.pos.javapos.shops.repository.ShopRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BranchMapper {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public BranchDto fromBranchToDto(Branch branch) throws JsonProcessingException {
        BranchDto branchDto = new BranchDto();
        BeanUtils.copyProperties(branch, branchDto);
        Map<String, Object> branchObject = objectMapper.readValue(branch.getBranch_object(),Map.class);
        branchDto.setBranch_object(branchObject);
        return branchDto;
    }

    public Branch fromBranchRequestDto(BranchDto branchDto) throws JsonProcessingException {
        Branch branch = new Branch();
        BeanUtils.copyProperties(branchDto, branch);
        branch.setBranch_object(objectMapper.writeValueAsString(branchDto.getBranch_object()));
        return branch;
    }

    public BranchResponseDto fromBranchToResponseDto(Branch branch) throws JsonProcessingException {
        BranchResponseDto branchResponseDto = new BranchResponseDto();
        BeanUtils.copyProperties(branch, branchResponseDto);
        Map<String, Object> branchObject = objectMapper.readValue(branch.getBranch_object(),Map.class);
        branchResponseDto.setBranch_object(branchObject);
        Shop shop = shopRepository.findById(branch.getShop().getId()).orElseThrow(() -> new RuntimeException("Shop not found"));
        UserDto createdBy = findUser(branch.getCreatedBy());
        UserDto updatedBy = findUser(branch.getUpdatedBy());
        branchResponseDto.setShop(shopMapper.fromShopToResponseDto(shop));
        branchResponseDto.setCreatedBy(createdBy);
        branchResponseDto.setUpdatedBy(updatedBy);
        return branchResponseDto;
    }

    public Branch fromBranchResponseDtoToBranch(BranchResponseDto branchResponseDto) throws JsonProcessingException {
        Branch branch = new Branch();
        BeanUtils.copyProperties(branchResponseDto, branch);
        branch.setBranch_object(objectMapper.writeValueAsString(branchResponseDto.getBranch_object()));
        return branch;
    }

    private UserDto findUser(String userId){
        User user =userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.fromUserToDto(user);
    }
}
