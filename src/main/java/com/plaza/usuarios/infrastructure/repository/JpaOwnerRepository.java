package com.plaza.usuarios.infrastructure.repository;

import com.plaza.usuarios.domain.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaOwnerRepository extends JpaRepository <Owner, Long> {

    Optional<Owner> findByEmail(String email);
}
