package com.immo.repositories;

import com.immo.entities.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 29/10/2019.
 */
public interface ContratRepository extends JpaRepository<Contrat, Integer> {
    public Contrat findById(int id);
}
