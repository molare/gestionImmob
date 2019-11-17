package com.immo.service;

import com.immo.entities.Locater;

import java.util.List;

/**
 * Created by olivier on 09/10/2019.
 */
public interface LocaterService {
    public List<Locater> getAll();
    public Locater add(Locater locater);
    public Locater update(Locater locater);
    public Locater findById(int id);
    public void delete(int id);
    public int countLocater();
}
