package com.immo.repositories;


import com.immo.entities.TypeLocative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypeLocativeRepository extends JpaRepository<TypeLocative, Integer> {
    public TypeLocative findById(int id);
}
