package com.immo.serviceImpl;

import com.immo.entities.MoyenPay;
import com.immo.repositories.MoyenPayRepository;
import com.immo.service.MoyenPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("moyenPayService")
public class MoyenPayServiceImpl implements MoyenPayService {
    @Autowired
    private MoyenPayRepository moyenPayRepository;

    @Override
    public List<MoyenPay> getAll() {
        return moyenPayRepository.findAll();
    }

    @Override
    public MoyenPay add(MoyenPay moyenPay) {
        return moyenPayRepository.save(moyenPay);
    }

    @Override
    public MoyenPay update(MoyenPay moyenPay) {
        if(moyenPay.getId() ==0){
            return moyenPayRepository.save(moyenPay);
        }
        return moyenPayRepository.saveAndFlush(moyenPay);
    }

    @Override
    public MoyenPay findById(int id) {
        return moyenPayRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        moyenPayRepository.deleteById(id);
    }
}
