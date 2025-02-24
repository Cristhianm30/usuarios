package com.plaza.usuarios.domain.port;

import com.plaza.usuarios.domain.model.Owner;

import java.util.Optional;

public interface OwnerRepository {
    Owner save(Owner owner);

    Optional<Owner> findByEmail(String email);
}
