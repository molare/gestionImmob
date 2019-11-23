package com.immo.repositories;


import com.immo.entities.Devis;
import com.immo.entities.Twon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TwonRepository extends JpaRepository<Twon, Integer> {
    public Twon findById(int id);
}
