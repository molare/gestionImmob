package com.immo.service;

import com.immo.entities.Twon;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TwonService {
    public List<Twon> getAll();
    public Twon add(Twon Twon);
    public Twon update(Twon Twon);
    public Twon findById(int id);
    public void delete(int id);
}
