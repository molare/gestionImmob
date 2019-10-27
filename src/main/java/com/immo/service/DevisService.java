package com.immo.service;

import com.immo.entities.Devis;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface DevisService {
    public List<Devis> getAll();
    public Devis add(Devis Devis);
    public Devis update(Devis Devis);
    public Devis findById(int id);
    public void delete(int id);
}
