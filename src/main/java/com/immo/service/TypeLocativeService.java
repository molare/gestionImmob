package com.immo.service;

import com.immo.entities.TypeLocative;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypeLocativeService {
    public List<TypeLocative> getAll();
    public TypeLocative add(TypeLocative typeLocative);
    public TypeLocative update(TypeLocative typeLocative);
    public TypeLocative findById(int id);
    public void delete(int id);
}
