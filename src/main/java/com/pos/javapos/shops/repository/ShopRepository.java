package com.pos.javapos.shops.repository;

import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long>, JpaSpecificationExecutor<Shop> {

    @Query(value = "select s.* from shop as s inner join users as u on s.id = u.shop_id where s.id = u.shop_id and u.id = :userId", nativeQuery = true)
    List<Shop> getShopsByUserId(@Param("userId") Long userId);


}
