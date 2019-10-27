package com.immo.serviceImpl;


import com.immo.entities.TypeLocative;
import com.immo.repositories.TypeLocativeRepository;
import com.immo.service.TypeLocativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("typeLocativeService")
public class TypeLocativeServiceImpl implements TypeLocativeService {
    @Autowired
    private TypeLocativeRepository typeLocativeRepository;

    @Override
    public List<TypeLocative> getAll() {
        return typeLocativeRepository.findAll();
    }

    @Override
    public TypeLocative add(TypeLocative typeLocative) {
        return typeLocativeRepository.save(typeLocative);
    }

    @Override
    public TypeLocative update(TypeLocative typeLocative) {
        if(typeLocative.getId() ==0){
            return typeLocativeRepository.save(typeLocative);
        }
        return typeLocativeRepository.saveAndFlush(typeLocative);
    }

    @Override
    public TypeLocative findById(int id) {
        return typeLocativeRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        typeLocativeRepository.deleteById(id);
    }
}
