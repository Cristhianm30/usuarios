package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidOwnerAgeException;
import com.plaza.usuarios.domain.model.Owner;
import com.plaza.usuarios.domain.port.OwnerRepository;
import com.plaza.usuarios.domain.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public Owner createOwner(Owner owner) {
        validateOwner(owner);
        owner.setPassword(owner.getPassword()); //user.setPassword(encryptPassword(user.getPassword()));

        return ownerRepository.save(owner);
    }

    private void validateOwner(Owner owner) {
        validateOwnerAge(owner.getBirthDate());
        validateEmail(owner.getEmail());
    }

    private void validateOwnerAge(LocalDate birthDate) {
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

//    private String encryptPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }
}
