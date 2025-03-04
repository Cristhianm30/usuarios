package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.*;
import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.port.UserRepository;
import com.plaza.usuarios.domain.service.UserService;
import com.plaza.usuarios.infrastructure.dto.UserDto;
import com.plaza.usuarios.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createOwner(User user) {
        validateOwner(user);
        user.setRole(roleService.getOwnerRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    @Override
    public User createEmployee(User user) {
        validateEmployee(user);
        user.setRole(roleService.getEmployeeRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    private void validateOwner(User user) {
        // Campos obligatorios para propietarios
        if (user.getBirthDate() == null) {
            throw new InvalidOwnerAgeException("La fecha de nacimiento es obligatoria");
        }
        validateOwnerFields(user);
        validateAge(user.getBirthDate());
        validateEmail(user.getEmail());
        validateDocumentNumber(user.getDocumentNumber());
        validateCellPhone(user.getCellPhone(), 13);
    }

    private void validateEmployee(User user) {
        validateEmail(user.getEmail());
        validateDocumentNumber(user.getDocumentNumber());
    }


    private void validateAge(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new InvalidOwnerAgeException("El propietario debe ser mayor de edad.");
        }
    }

    private void validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailFormatException("El formato del correo electrónico no es válido.");
        }
    }

    private void validateDocumentNumber(String documentNumber) {
        String regex = "^\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(documentNumber);
        if (!matcher.matches()) {
            throw new InvalidDocumentNumberException("El formato del número de documento no es válido.");
        }
    }

    private void validateCellPhone(String cellPhone, int maxLength) {
        String regex = "^\\+?\\d{1," + (maxLength - 1) + "}$"; // Ej: +573005698325 (13 caracteres)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhone);
        if (!matcher.matches()) {
            throw new InvalidCellPhoneException("Formato de celular inválido");
        }
    }

    private void validateOwnerFields(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new InvalidUserException("El nombre es obligatorio");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new InvalidUserException("El apellido es obligatorio");
        }
        if (user.getBirthDate() == null) {
            throw new InvalidUserException("La fecha de nacimiento es obligatoria para propietarios");
        }
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (user.getPassword() == null) {
            throw new IllegalStateException("El usuario no tiene contraseña asignada");
        }

        return UserMapper.toDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Propietario no encontrado"));
    }



}
