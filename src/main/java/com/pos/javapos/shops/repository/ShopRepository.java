package com.pos.javapos.shops.repository;

import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.shops.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query(value = "select * from shop as s where s.id in (select u.shop_id from shop_user as u where u.user_id = :userId)", nativeQuery = true)
    List<Shop> getShopsByUserId(@Param("userId") Long userId);


}
