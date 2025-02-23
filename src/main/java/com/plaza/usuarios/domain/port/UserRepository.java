package com.plaza.usuarios.domain.port;

import com.plaza.usuarios.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);
}
