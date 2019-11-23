package com.immo.repositories;


import com.immo.entities.EnterpriseType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface EnterpriseTypeRepository extends JpaRepository<EnterpriseType, Integer> {
    public EnterpriseType findById(int id);
}
