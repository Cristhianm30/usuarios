package com.plaza.usuarios.application.service;

import com.plaza.usuarios.application.exception.InvalidDocumentNumberException;
import com.plaza.usuarios.application.exception.InvalidEmailFormatException;
import com.plaza.usuarios.application.exception.InvalidOwnerAgeException;
import com.plaza.usuarios.application.exception.OwnerNotFoundException;
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

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public Owner createOwner(Owner owner) {
        validateOwner(owner);
        owner.setRole(roleService.getOwnerRole());
        owner.setPassword(owner.getPassword()); //user.setPassword(encryptPassword(user.getPassword()));

        return ownerRepository.save(owner);
    }

    @Override
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException("Propietario no encontrado"));
    }

    private void validateOwner(Owner owner) {
        validateOwnerAge(owner.getBirthDate());
        validateEmail(owner.getEmail());
        validateDocumentNumber(owner.getDocumentNumber());
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

    private void validateDocumentNumber(String documentNumber) {
        String regex = "^\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(documentNumber);
        if (!matcher.matches()) {
            throw new InvalidDocumentNumberException("El formato del número de documento no es válido.");
        }
    }

//    private String encryptPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }
}
