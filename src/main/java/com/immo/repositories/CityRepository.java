package com.immo.repositories;

import com.immo.entities.City;
import com.immo.entities.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CityRepository extends JpaRepository<City,Integer> {
    public City findById(int id);
    public List<City> findByCommune(Commune commune);
}
