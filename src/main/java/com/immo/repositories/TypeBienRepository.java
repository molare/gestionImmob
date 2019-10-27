package com.immo.repositories;


import com.immo.entities.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypeBienRepository extends JpaRepository<TypeBien, Integer> {
    public TypeBien findById(int id);
}
