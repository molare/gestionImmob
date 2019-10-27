package com.immo.repositories;


import com.immo.entities.Locative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface LocativeRepository extends JpaRepository<Locative, Integer> {
    public Locative findById(int id);
   /* public List<Locative> findByProperty(int id);
    public List<Locative> findByCity(int id);*/
}
