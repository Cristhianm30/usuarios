package com.plaza.usuarios.application.service;

import com.plaza.usuarios.UserDataProvider;

import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.port.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername_exist() {
        // Given
        User user = UserDataProvider.userWithRoleMock(); // Usar el DataProvider
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("john.doe@example.com");

        // Then
        assertNotNull(userDetails);
        assertEquals("john.doe@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROPIETARIO")));
    }

    @Test
    public void testLoadUserByUsername_noExist() {
        // Given
        when(userRepository.findByEmail("no.exist@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("no.exist@example.com"));
    }
}