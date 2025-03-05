package com.plaza.usuarios.infrastructure.controller;

import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.domain.port.UserRepository;
import com.plaza.usuarios.domain.service.UserService;
import com.plaza.usuarios.infrastructure.config.JwtUtils;
import com.plaza.usuarios.infrastructure.dto.UserDto;
import com.plaza.usuarios.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/owner")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<User> createOwner(@RequestBody User user) {
        User createdUser = userService.createOwner(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = UserMapper.toDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        String email = jwtUtils.extractEmail(token.substring(7));
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<User> createEmployee(@RequestBody User employeeRequest) {
        User createdEmployee = userService.createEmployee(employeeRequest);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<User> createClient(@RequestBody User clientRequest) {
        User createdClient = userService.createClient(clientRequest);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }



}
