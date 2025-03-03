package com.plaza.usuarios;

import com.plaza.usuarios.domain.model.Role;
import com.plaza.usuarios.domain.model.User;

import java.time.LocalDate;

public class UserDataProvider {

    public static User validUserMock() {
        return User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+573005698325")
                .birthDate(LocalDate.now().minusYears(20))
                .email("john.doe@example.com")
                .password("password123")
                .build();
    }

    public static User invalidEmailUserMock() {
        return User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+573005698325")
                .birthDate(LocalDate.now().minusYears(20))
                .email("invalid_email")
                .password("password123")
                .build();
    }

    public static User invalidDocumentNumberUserMock() {
        return User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("invalid_document")
                .cellPhone("+573005698325")
                .birthDate(LocalDate.now().minusYears(20))
                .email("john.doe@example.com")
                .password("password123")
                .build();
    }

    public static User invalidAgeUserMock() {
        return User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+573005698325")
                .birthDate(LocalDate.now().minusYears(15))
                .email("john.doe@example.com")
                .password("password123")
                .build();
    }

    public static User userWithRoleMock() {
        return User.builder()
                .name("John")
                .lastName("Doe")
                .documentNumber("1234567890")
                .cellPhone("+573005698325")
                .birthDate(LocalDate.now().minusYears(20))
                .email("john.doe@example.com")
                .password("password123")
                .role(Role.builder().name("PROPIETARIO").build())
                .build();
    }
}
