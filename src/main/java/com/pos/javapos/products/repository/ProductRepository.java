package com.pos.javapos.products.repository;

import com.pos.javapos.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT sku FROM products ORDER BY id DESC LIMIT 1", nativeQuery = true)
            String findLastId();

    @Query(value = "select product_name from products where product_name = :productName", nativeQuery = true)
    String existsByProductName(@Param("productName") String productName);
}
