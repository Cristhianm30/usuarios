package com.plaza.usuarios.application.service;

import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.port.RoleRepository;
import com.plaza.usuarios.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getOwnerRole() {
        return roleRepository.findByName("PROPIETARIO")
                .orElseThrow(() -> new RuntimeException("Rol de propietario no encontrado"));
    }

    public Role getEmployeeRole() {
        return roleRepository.findByName("EMPLEADO")
                .orElseThrow(() -> new RuntimeException("Rol de empleado no encontrado"));
    }

    public Role getClientRole() {
        return roleRepository.findByName("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol de cliente no encontrado"));
    }
}
