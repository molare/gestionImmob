package com.immo.service;

import com.immo.entities.StatutPay;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
public interface StatutPayService {
    public List<StatutPay> getAll();
    public StatutPay add(StatutPay statutPay);
    public StatutPay update(StatutPay statutPay);
    public StatutPay findById(int id);
    public void delete(int id);
}
