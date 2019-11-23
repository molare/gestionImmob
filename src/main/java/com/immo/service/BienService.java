package com.immo.service;

import com.immo.entities.City;
import com.immo.entities.Bien;
import com.immo.entities.Property;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface BienService {
    public List<Bien> getAll();
    public Bien add(Bien bien);
    public Bien update(Bien bien);
    public Bien findById(int id);
    public void delete(int id);
    public List<Bien> findByProperty(Property property);
    public List<Bien> findByCity(City city);
    public int countBien();
    public List<Bien> export(int cpt, HttpServletRequest request);
}
