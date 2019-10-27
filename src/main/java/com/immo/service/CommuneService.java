package com.immo.service;

import com.immo.entities.Commune;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CommuneService {
    public List<Commune> getAll();
    public Commune add(Commune commune);
    public Commune update(Commune commune);
    public Commune findById(int id);
    public void delete(int id);
}
