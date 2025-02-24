package com.plaza.usuarios.domain.port;

import com.plaza.usuarios.domain.model.Role;

import java.util.Optional;


public interface RoleRepository {

    Optional<Role> findByName(String name);
}
