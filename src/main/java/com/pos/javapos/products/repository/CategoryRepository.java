package com.pos.javapos.products.repository;

import com.pos.javapos.products.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
