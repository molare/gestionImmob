package com.immo.serviceImpl;

import com.immo.entities.StatutPay;
import com.immo.repositories.StatutPayRepository;
import com.immo.service.StatutPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("statutPayService")
public class StatutPayServiceImpl implements StatutPayService {
    @Autowired
    private StatutPayRepository statutPayRepository;

    @Override
    public List<StatutPay> getAll() {
        return statutPayRepository.findAll();
    }

    @Override
    public StatutPay add(StatutPay moyenPay) {
        return statutPayRepository.save(moyenPay);
    }

    @Override
    public StatutPay update(StatutPay moyenPay) {
        if(moyenPay.getId() ==0){
            return statutPayRepository.save(moyenPay);
        }
        return statutPayRepository.saveAndFlush(moyenPay);
    }

    @Override
    public StatutPay findById(int id) {
        return statutPayRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        statutPayRepository.deleteById(id);
    }
}
