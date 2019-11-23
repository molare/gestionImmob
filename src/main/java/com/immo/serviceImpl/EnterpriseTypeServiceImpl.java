package com.immo.serviceImpl;

import com.immo.entities.Enterprise;
import com.immo.entities.EnterpriseType;
import com.immo.repositories.EnterpriseRepository;
import com.immo.repositories.EnterpriseTypeRepository;
import com.immo.service.EnterpriseService;
import com.immo.service.EnterpriseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("enterpriseTypeService")
public class EnterpriseTypeServiceImpl implements EnterpriseTypeService {
    @Autowired
    private EnterpriseTypeRepository enterpriseTypeRepository;

    @Override
    public List<EnterpriseType> getAll() {
        return enterpriseTypeRepository.findAll();
    }

    @Override
    public EnterpriseType add(EnterpriseType enterpriseType) {
        return enterpriseTypeRepository.save(enterpriseType);
    }

    @Override
    public EnterpriseType update(EnterpriseType enterpriseType) {
        if(enterpriseType.getId() ==0){
            return enterpriseTypeRepository.save(enterpriseType);
        }
        return enterpriseTypeRepository.saveAndFlush(enterpriseType);
    }

    @Override
    public EnterpriseType findById(int id) {
        return enterpriseTypeRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        enterpriseTypeRepository.deleteById(id);
    }
}
