package com.plaza.usuarios.domain.service;

import com.plaza.usuarios.domain.model.User;

public interface UserService {
    User createOwner(User user);
    User getUserById(Long id);
}
