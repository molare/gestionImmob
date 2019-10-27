package com.immo.service;

import com.immo.entities.MoyenPay;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface MoyenPayService {
    public List<MoyenPay> getAll();
    public MoyenPay add(MoyenPay moyenPay);
    public MoyenPay update(MoyenPay moyenPay);
    public MoyenPay findById(int id);
    public void delete(int id);
}
