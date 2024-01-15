package com.pos.javapos.shops.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.shops.dto.BranchDto;
import com.pos.javapos.shops.dto.BranchResponseDto;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import com.pos.javapos.shops.mapper.BranchMapper;
import com.pos.javapos.shops.repository.BranchRepository;
import com.pos.javapos.shops.repository.ShopRepository;
import com.pos.javapos.shops.service.BranchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BranchServiceIml implements BranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchMapper branchMapper;
    @Autowired
    private ShopRepository shopRepository;
    @Override
    public List<BranchDto> getBranchesByShopId(Long shopId) {
        List<Branch> branches =branchRepository.getBranchesByShopId(shopId);
        return branches.stream().map(branch -> {
            try {
                return branchMapper.fromBranchToDto(branch);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();

    }

    @Override
    public BranchResponseDto addBranch(BranchDto branchDto) throws JsonProcessingException {
        Shop shop = shopRepository.findById(branchDto.getShop_id()).orElseThrow(() -> new RuntimeException("Shop not found"));
        Branch branch = branchMapper.fromBranchRequestDto(branchDto);
        branch.setShop(shop);
        branchRepository.save(branch);
        return branchMapper.fromBranchToResponseDto(branch);
    }

    @Override
    public BranchResponseDto updateBranch(BranchDto branchDto) throws JsonProcessingException {
        try {
            Branch branch = branchRepository.findById(branchDto.getId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            Shop shop = shopRepository.findById(branchDto.getShop_id()).orElseThrow(() -> new RuntimeException("Shop not found"));
            BeanUtils.copyProperties(branchDto, branch);
            branch.setShop(shop);
            branchRepository.save(branch);
            return branchMapper.fromBranchToResponseDto(branch);
        }catch (Exception e){
            throw new RuntimeException("Branch update failed");
        }

    }

    @Override
    public Void deleteBranch(Long id) {
        try {
            branchRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Branch delete failed");
        }
        return null;
    }


}
