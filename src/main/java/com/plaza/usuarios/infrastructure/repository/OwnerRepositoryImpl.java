package com.plaza.usuarios.infrastructure.repository;

import com.plaza.usuarios.domain.model.Owner;
import com.plaza.usuarios.domain.port.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {

    @Autowired
    private JpaOwnerRepository jpaOwnerRepository;

    @Override
    public Owner save(Owner owner) {
        return jpaOwnerRepository.save(owner);
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        return jpaOwnerRepository.findByEmail(email);
    }

}
