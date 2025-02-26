package com.plaza.usuarios.domain.service;

import com.plaza.usuarios.domain.model.Owner;

public interface OwnerService {
    Owner createOwner(Owner owner);
    Owner getOwnerById(Long id);
}
