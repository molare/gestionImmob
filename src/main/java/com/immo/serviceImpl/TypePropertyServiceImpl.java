package com.immo.serviceImpl;

import com.immo.entities.TypeProperty;
import com.immo.repositories.TypePropertyRepository;
import com.immo.service.TypePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("typePropertyService")
public class TypePropertyServiceImpl implements TypePropertyService {
    @Autowired
    private TypePropertyRepository typePropertyRepository;

    @Override
    public List<TypeProperty> getAll() {
        return typePropertyRepository.findAll();
    }

    @Override
    public TypeProperty add(TypeProperty typeproperty) {
        return typePropertyRepository.save(typeproperty);
    }

    @Override
    public TypeProperty update(TypeProperty typeproperty) {
        if(typeproperty.getId() ==0){
            return typePropertyRepository.save(typeproperty);
        }
        return typePropertyRepository.saveAndFlush(typeproperty);
    }

    @Override
    public TypeProperty findById(int id) {
        return typePropertyRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        typePropertyRepository.deleteById(id);
    }
}
