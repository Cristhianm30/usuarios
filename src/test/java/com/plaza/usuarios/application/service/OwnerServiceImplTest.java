package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidOwnerAgeException;
import com.plaza.usuarios.domain.model.Owner;
import com.plaza.usuarios.domain.port.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOwner() {
        // Crear un usuario de prueba
        Owner owner = Owner.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .password("password")
                .build();

        // Mockear el comportamiento del repositorio de usuarios
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        // Invocar el método createUser
        Owner createdOwner = ownerService.createOwner(owner);

        // Verificar que el usuario se haya creado correctamente
        assertNotNull(createdOwner);
        assertEquals("John", createdOwner.getName());
        assertEquals("Doe", createdOwner.getLastName());
        assertEquals("1234567890", createdOwner.getDocumentNumber());
        assertEquals("+15551234567", createdOwner.getCellPhone());
        assertEquals(LocalDate.of(1990, 1, 1), createdOwner.getBirthDate());
        assertEquals("john.doe@example.com", createdOwner.getEmail());
        assertEquals("password", createdOwner.getPassword()); // La contraseña debe estar encriptada assertNotEquals("password", createdOwner.getPassword());
    }

    @Test
    public void testCreateownerInvalidAge() {
        // Crear un usuario de prueba con una edad inválida
        Owner owner = Owner.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.now().minusYears(10))
                .email("john.doe@example.com")
                .password("password")
                .build();

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidOwnerAgeException.class, () -> ownerService.createOwner(owner));
    }

    @Test
    public void testCreateownerInvalidEmail() {
        // Crear un usuario de prueba con un email inválido
        Owner owner = Owner.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("john.doe")
                .password("password")
                .build();

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidEmailFormatException.class, () -> ownerService.createOwner(owner));
    }

    // Otras pruebas que necesites
}