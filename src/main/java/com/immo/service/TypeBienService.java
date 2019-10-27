package com.immo.service;

import com.immo.entities.Devis;
import com.immo.entities.TypeBien;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypeBienService {
    public List<TypeBien> getAll();
    public TypeBien add(TypeBien typeBien);
    public TypeBien update(TypeBien typeBien);
    public TypeBien findById(int id);
    public void delete(int id);
}
