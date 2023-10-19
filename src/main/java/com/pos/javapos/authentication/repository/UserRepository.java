package com.pos.javapos.authentication.repository;

import com.pos.javapos.authentication.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query(value = "select * from users as u where u.id in (select s.user_id from shop_user as s where s.shop_id = :shopId)", nativeQuery = true)
    Page<User> getUsersByShopId(@Param("shopId") Long shopId, Pageable pageable);
}
