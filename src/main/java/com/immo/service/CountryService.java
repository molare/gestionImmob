package com.immo.service;

import com.immo.entities.Country;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CountryService {
    public List<Country> getAll();
    public Country add(Country Country);
    public Country update(Country Country);
    public Country findById(int id);
    public void delete(int id);
}
