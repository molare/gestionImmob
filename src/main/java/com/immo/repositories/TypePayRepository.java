package com.immo.repositories;

import com.immo.entities.TypePay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypePayRepository extends JpaRepository<TypePay, Integer>{
    public TypePay findById(int id);
}
