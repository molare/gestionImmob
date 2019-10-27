package com.immo.repositories;


import com.immo.entities.Bien;
import com.immo.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface BienRepository extends JpaRepository<Bien, Integer> {
    public Bien findById(int id);
    public List<Bien> findByProperty(Property Property);
    public List<Bien> findByCity(int id);
}
