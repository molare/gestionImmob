package com.immo.repositories;

import com.immo.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 09/10/2019.
 */
public interface PropertyRepository extends JpaRepository<Property,Integer> {
    public Property findById(int id);
}
