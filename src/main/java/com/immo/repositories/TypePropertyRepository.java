package com.immo.repositories;

import com.immo.entities.TypeProperty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypePropertyRepository extends JpaRepository<TypeProperty, Integer>{
    public TypeProperty findById(int id);
}
