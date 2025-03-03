package com.plaza.usuarios.infrastructure.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private String documentNumber;
    private String cellPhone;
    private String password;
    private LocalDate birthDate;
    private String email;
    private String role;
}
