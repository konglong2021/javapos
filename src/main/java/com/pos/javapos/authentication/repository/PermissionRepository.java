package com.pos.javapos.authentication.repository;

import com.pos.javapos.authentication.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Boolean existsByName(String name);

    Optional<Permission> findByName(String name);
}
