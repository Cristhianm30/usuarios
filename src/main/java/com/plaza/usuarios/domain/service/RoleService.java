package com.plaza.usuarios.domain.service;

import com.plaza.usuarios.domain.model.Role;

public interface RoleService {
    Role getOwnerRole();
    Role getEmployeeRole();
}
