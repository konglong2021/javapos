package com.pos.javapos.authentication.repository;

import com.pos.javapos.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName(String roleName);
   Boolean existsByName(String roleName);
}
