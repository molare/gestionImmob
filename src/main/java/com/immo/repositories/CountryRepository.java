package com.immo.repositories;


import com.immo.entities.Country;
import com.immo.entities.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CountryRepository extends JpaRepository<Country, Integer> {
    public Country findById(int id);
}
