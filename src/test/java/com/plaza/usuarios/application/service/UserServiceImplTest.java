package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidUserAgeException;
import com.plaza.usuarios.domain.model.User;
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

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
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

        // Mockear el comportamiento del repositorio de usuarios
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Invocar el método createUser
        User createdUser = userService.createUser(user);

        // Verificar que el usuario se haya creado correctamente
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("1234567890", createdUser.getDocumentNumber());
        assertEquals("+15551234567", createdUser.getCellPhone());
        assertEquals(LocalDate.of(1990, 1, 1), createdUser.getBirthDate());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertNotEquals("password", createdUser.getPassword()); // La contraseña debe estar encriptada
    }

    @Test
    public void testCreateUserInvalidAge() {
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

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidUserAgeException.class, () -> userService.createUser(user));
    }

    @Test
    public void testCreateUserInvalidEmail() {
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

        // Verificar que se lance una excepción al intentar crear el usuario
        assertThrows(InvalidEmailFormatException.class, () -> userService.createUser(user));
    }

    // Otras pruebas que necesites
}