package com.pos.javapos.shops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pos.javapos.shops.dto.BranchDto;
import com.pos.javapos.shops.dto.BranchResponseDto;

import java.util.List;

public interface BranchService {
    List<BranchDto> getBranchesByShopId(Long shopId);

    BranchResponseDto addBranch(BranchDto branchDto) throws JsonProcessingException;

}
