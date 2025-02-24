package com.plaza.usuarios.infrastructure.repository;

import com.plaza.usuarios.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
