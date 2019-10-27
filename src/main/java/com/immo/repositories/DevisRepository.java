package com.immo.repositories;


import com.immo.entities.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface DevisRepository extends JpaRepository<Devis, Integer> {
    public Devis findById(int id);
}
