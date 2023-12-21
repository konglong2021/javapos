package com.pos.javapos.shops.repository;

import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Long> {

    @Query(value = "select * from branch as b where b.shop_id = :shopId", nativeQuery = true)
    List<Branch> getBranchesByShopId(@Param("shopId") Long shopId);
}
