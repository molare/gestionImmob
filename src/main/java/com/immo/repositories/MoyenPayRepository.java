package com.immo.repositories;

import com.immo.entities.MoyenPay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olivier on 02/10/2019.
 */
public interface MoyenPayRepository extends JpaRepository<MoyenPay, Integer>{
    public MoyenPay findById(int id);
}
