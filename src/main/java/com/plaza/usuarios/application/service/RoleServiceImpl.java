package com.plaza.usuarios.application.service;

import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.port.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl {

    @Autowired
    private RoleRepository roleRepository;

    public Role getOwnerRole() {
        return roleRepository.findByName("PROPIETARIO")
                .orElseThrow(() -> new RuntimeException("Rol de propietario no encontrado"));
    }
}
