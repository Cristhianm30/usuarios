package com.plaza.usuarios.domain.service;

import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.infrastructure.dto.UserDto;

public interface UserService {
    User createOwner(User user);
    User getUserById(Long id);
    User createEmployee(User user);
    UserDto getUserByEmail(String email);
}
