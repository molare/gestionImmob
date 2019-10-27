package com.immo.service;

import com.immo.entities.TypePay;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface TypePayService {
    public List<TypePay> getAll();
    public TypePay add(TypePay typePay);
    public TypePay update(TypePay typePay);
    public TypePay findById(int id);
    public void delete(int id);
}
