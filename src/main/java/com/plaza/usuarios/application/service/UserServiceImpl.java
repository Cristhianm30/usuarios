package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidUserAgeException;
import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.port.UserRepository;
import com.plaza.usuarios.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        validateUser(user);
        user.setPassword(user.getPassword());

        return userRepository.save(user);
    }

    private void validateUser(User user) {
        validateUserAge(user.getBirthDate());
        validateEmail(user.getEmail());
    }

    private void validateUserAge(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new InvalidUserAgeException("El usuario debe ser mayor de edad.");
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

//    private String encryptPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }
}
