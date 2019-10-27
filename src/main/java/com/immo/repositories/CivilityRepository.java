package com.immo.repositories;

import com.immo.entities.Civility;
import com.immo.entities.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CivilityRepository extends JpaRepository<Civility, Integer>{
    public Civility findById(int id);
}
