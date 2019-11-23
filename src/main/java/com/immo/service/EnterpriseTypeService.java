package com.immo.service;

import com.immo.entities.EnterpriseType;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface EnterpriseTypeService {
    public List<EnterpriseType> getAll();
    public EnterpriseType add(EnterpriseType enterpriseType);
    public EnterpriseType update(EnterpriseType enterpriseType);
    public EnterpriseType findById(int id);
    public void delete(int id);
}
