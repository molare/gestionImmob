package com.immo.service;

import com.immo.entities.Property;

import java.util.List;

/**
 * Created by olivier on 09/10/2019.
 */
public interface PropertyService {
    public List<Property> getAll();
    public Property add(Property property);
    public Property update(Property property);
    public Property findById(int id);
    public void delete(int id);
}
