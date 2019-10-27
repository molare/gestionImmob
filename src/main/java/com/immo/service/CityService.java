package com.immo.service;

import com.immo.entities.City;
import com.immo.entities.Commune;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CityService {
    public List<City> getAll();
    public City add(City city);
    public City update(City city);
    public City findById(int id);
    public void delete(int id);
    public List<City> findByCommune(Commune commune);
}
