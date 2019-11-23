package com.immo.service;

import com.immo.entities.Enterprise;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface EnterpriseService {
    public List<Enterprise> getAll();
    public Enterprise add(Enterprise enterprise);
    public Enterprise update(Enterprise enterprise);
    public Enterprise findById(int id);
    public void delete(int id);
}
