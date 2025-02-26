package com.plaza.usuarios.infrastructure.controller;

import com.plaza.usuarios.domain.model.Owner;
import com.plaza.usuarios.domain.service.OwnerService;
import com.plaza.usuarios.infrastructure.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Owner> createUser(@RequestBody Owner owner) {
        Owner createdOwner = ownerService.createOwner(owner);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        OwnerDto ownerDto = convertToDto(owner);
        return new ResponseEntity<>( ownerDto, HttpStatus.OK);
    }

    private OwnerDto convertToDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setLastName(owner.getLastName());
        ownerDto.setDocumentNumber(owner.getDocumentNumber());
        ownerDto.setBirthDate(owner.getBirthDate());
        ownerDto.setCellPhone(owner.getCellPhone());
        ownerDto.setEmail(owner.getEmail());
        ownerDto.setRole(owner.getRole().getId());
        return ownerDto;
    }


}
