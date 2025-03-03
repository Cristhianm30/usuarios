package com.plaza.usuarios.application.service;

import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testGetOwnerRole_exist() {
        // Given
        Role role = Role.builder().name("PROPIETARIO").build();
        when(roleRepository.findByName("PROPIETARIO")).thenReturn(Optional.of(role));

        // When
        Role ownerRole = roleService.getOwnerRole();

        // Then
        assertNotNull(ownerRole);
        assertEquals("PROPIETARIO", ownerRole.getName());
    }

    @Test
    public void testGetOwnerRole_noExist() {
        // Given
        when(roleRepository.findByName("PROPIETARIO")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> roleService.getOwnerRole());
    }
}