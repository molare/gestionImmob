package com.immo.serviceImpl;

import com.immo.entities.TypePay;
import com.immo.repositories.TypePayRepository;
import com.immo.service.TypePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("typePayService")
public class TypePayServiceImpl implements TypePayService {
    @Autowired
    private TypePayRepository typePayRepository;

    @Override
    public List<TypePay> getAll() {
        return typePayRepository.findAll();
    }

    @Override
    public TypePay add(TypePay typePay) {
        return typePayRepository.save(typePay);
    }

    @Override
    public TypePay update(TypePay typePay) {
        if(typePay.getId() ==0){
            return typePayRepository.save(typePay);
        }
        return typePayRepository.saveAndFlush(typePay);
    }

    @Override
    public TypePay findById(int id) {
        return typePayRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        typePayRepository.deleteById(id);
    }
}
