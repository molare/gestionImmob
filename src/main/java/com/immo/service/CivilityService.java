package com.immo.service;

import com.immo.entities.Civility;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface CivilityService {
    public List<Civility> getAll();
    public Civility add(Civility Civility);
    public Civility update(Civility Civility);
    public Civility findById(int id);
    public void delete(int id);
}
