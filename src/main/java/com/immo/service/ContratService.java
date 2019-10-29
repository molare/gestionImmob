package com.immo.service;

import com.immo.entities.Contrat;
import com.immo.entities.City;
import com.immo.entities.Property;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface ContratService {
    public List<Contrat> getAll();
    public Contrat add(Contrat contrat);
    public Contrat update(Contrat contrat);
    public Contrat findById(int id);
    public void delete(int id);
   
}
