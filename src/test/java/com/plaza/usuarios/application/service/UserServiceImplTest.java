package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidDocumentNumberException;
import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidOwnerAgeException;
import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.port.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @InjectMocks
    private UserServiceImpl ownerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOwner() {
        // Crear un usuario de prueba
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .password("password")
                .build();
        Role role = Role.builder()
                .id(1L)
                .name("Propietario")
                .build();

        // Mockear el comportamiento de las dependencias
        when(roleService.getOwnerRole()).thenReturn(role);

        // Mockear el comportamiento del repositorio de usuarios
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Invocar el método createUser
        User createdUser = ownerService.createOwner(user);

        // Verificar que el usuario se haya creado correctamente
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("1234567890", createdUser.getDocumentNumber());
        assertEquals("+15551234567", createdUser.getCellPhone());
        assertEquals(LocalDate.of(1990, 1, 1), createdUser.getBirthDate());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals("password", createdUser.getPassword()); // La contraseña debe estar encriptada assertNotEquals("password", createdOwner.getPassword());
    }

    @Test
    public void testCreateownerInvalidAge() {
        // Crear un usuario de prueba con una edad inválida
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.now().minusYears(10))
                .email("john.doe@example.com")
                .password("password")
                .build();
        Role role = Role.builder()
                .id(1L)
                .name("Propietario")
                .build();

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidOwnerAgeException.class, () -> ownerService.createOwner(user));
    }

    @Test
    public void testCreateownerInvalidEmail() {
        // Crear un usuario de prueba con un email inválido
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("john.doe")
                .password("password")
                .build();
        Role role = Role.builder()
                .id(1L)
                .name("Propietario")
                .build();

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidEmailFormatException.class, () -> ownerService.createOwner(user));
    }

    @Test
    public void testCreateownerInvalidDocumentNumber() {
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("12345678912")
                .cellPhone("+15551234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("john.doe@gmail.com")
                .password("password")
                .build();
        Role role = Role.builder()
                .id(1L)
                .name("Propietario")
                .build();


        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidDocumentNumberException.class, () -> ownerService.createOwner(user));
    }
}