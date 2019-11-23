package com.immo.repositories;


import com.immo.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {
    public Enterprise findById(int id);
}
