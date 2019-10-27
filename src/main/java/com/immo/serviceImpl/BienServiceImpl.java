package com.immo.serviceImpl;


import com.immo.entities.City;
import com.immo.entities.Bien;
import com.immo.entities.Property;
import com.immo.repositories.BienRepository;
import com.immo.service.BienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("bienService")
public class BienServiceImpl implements BienService {
    @Autowired
    private BienRepository bienRepository;

    @Override
    public List<Bien> getAll() {
        return bienRepository.findAll();
    }

    @Override
    public Bien add(Bien bien) {
        return bienRepository.save(bien);
    }

    @Override
    public Bien update(Bien bien) {
        if(bien.getId() ==0){
            return bienRepository.save(bien);
        }
        return bienRepository.saveAndFlush(bien);
    }

    @Override
    public Bien findById(int id) {
        return bienRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        bienRepository.deleteById(id);
    }

    @Override
    public List<Bien> findByProperty(Property property) {
        return bienRepository.findByProperty(property);
    }

    @Override
    public List<Bien> findByCity(City city) {
        return null;
    }
}
