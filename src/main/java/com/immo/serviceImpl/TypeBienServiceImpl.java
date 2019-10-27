package com.immo.serviceImpl;


import com.immo.entities.TypeBien;
import com.immo.repositories.TypeBienRepository;
import com.immo.service.TypeBienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("typeBienService")
public class TypeBienServiceImpl implements TypeBienService {
    @Autowired
    private TypeBienRepository typeBienRepository;

    @Override
    public List<TypeBien> getAll() {
        return typeBienRepository.findAll();
    }

    @Override
    public TypeBien add(TypeBien typeBien) {
        return typeBienRepository.save(typeBien);
    }

    @Override
    public TypeBien update(TypeBien typeBien) {
        if(typeBien.getId() ==0){
            return typeBienRepository.save(typeBien);
        }
        return typeBienRepository.saveAndFlush(typeBien);
    }

    @Override
    public TypeBien findById(int id) {
        return typeBienRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        typeBienRepository.deleteById(id);
    }
}
