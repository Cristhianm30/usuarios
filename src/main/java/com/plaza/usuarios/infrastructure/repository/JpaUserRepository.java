package com.plaza.usuarios.infrastructure.repository;

import com.plaza.usuarios.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);
}
