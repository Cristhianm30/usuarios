package com.plaza.usuarios.application.service;


import com.plaza.usuarios.UserDataProvider;
import com.plaza.usuarios.application.exception.InvalidDocumentNumberException;
import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidEmployeeException;
import com.plaza.usuarios.application.exception.InvalidOwnerAgeException;
import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.port.UserRepository;
import com.plaza.usuarios.infrastructure.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    // Pruebas unitarias
    @Test
    void testCreateOwner() {
        // Given
        Role ownerRole = Role.builder().id(1L).name("PROPIETARIO").build();
        User user = UserDataProvider.validUserMock(); // Usar el DataProvider

        // When
        when(roleService.getOwnerRole()).thenReturn(ownerRole);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createOwner(user);

        // Then
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("1234567890", createdUser.getDocumentNumber());
        assertEquals("+573005698325", createdUser.getCellPhone());
        assertEquals(LocalDate.now().minusYears(20), createdUser.getBirthDate());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(ownerRole, createdUser.getRole());

    }

    @Test
    public void testInvalidEmail(){
        // Given
        User user = UserDataProvider.invalidEmailUserMock(); // Usar el DataProvider

        // When & Then
        assertThrows(InvalidEmailFormatException.class, () -> userService.createOwner(user));
    }

    @Test
    public void testInvalidAge(){
        // Given
        User user = UserDataProvider.invalidAgeUserMock(); // Usar el DataProvider

        // When & Then
        assertThrows(InvalidOwnerAgeException.class, () -> userService.createOwner(user));
    }

    @Test
    public void testInvalidDocumentNumber(){
        // Given
        User user = UserDataProvider.invalidDocumentNumberUserMock(); // Usar el DataProvider

        // When & Then
        assertThrows(InvalidDocumentNumberException.class, () -> userService.createOwner(user));
    }

    @Test
    void createEmployee_ValidUser() {
        // Configurar mocks
        Role employeeRole = Role.builder().id(2L).name("EMPLEADO").build();
        User user = UserDataProvider.validUserMock();
        when(roleService.getEmployeeRole()).thenReturn(employeeRole);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Ejecutar
        User result = userService.createEmployee(user);

        // Verificar
        assertEquals("EMPLEADO", result.getRole().getName());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void getUserByEmail_ValidEmail() {
        User user = UserDataProvider.userWithRoleMock();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        UserDto result = userService.getUserByEmail("john.doe@example.com");

        assertEquals("John", result.getName());
        assertEquals("PROPIETARIO", result.getRole());
    }

    @Test
    void getUserByEmail_UserWithoutPassword() {
        User user = UserDataProvider.validUserMock();
        user.setPassword(null);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> userService.getUserByEmail("test@example.com"));
    }
}