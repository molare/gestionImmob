package com.immo.serviceImpl;

import com.immo.entities.Enterprise;
import com.immo.repositories.EnterpriseRepository;
import com.immo.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Override
    public List<Enterprise> getAll() {
        return enterpriseRepository.findAll();
    }

    @Override
    public Enterprise add(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Enterprise update(Enterprise enterprise) {
        if(enterprise.getId() ==0){
            return enterpriseRepository.save(enterprise);
        }
        return enterpriseRepository.saveAndFlush(enterprise);
    }

    @Override
    public Enterprise findById(int id) {
        return enterpriseRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        enterpriseRepository.deleteById(id);
    }
}
