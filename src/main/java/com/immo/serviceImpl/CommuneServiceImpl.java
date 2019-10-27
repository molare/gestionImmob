package com.immo.serviceImpl;

import com.immo.entities.Commune;
import com.immo.repositories.CommuneRepository;
import com.immo.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("communeService")
public class CommuneServiceImpl implements CommuneService {
    @Autowired
    private CommuneRepository communeRepository;

    @Override
    public List<Commune> getAll() {
        return communeRepository.findAll();
    }

    @Override
    public Commune add(Commune commune) {
        return communeRepository.save(commune);
    }

    @Override
    public Commune update(Commune commune) {
        if(commune.getId() ==0){
            return communeRepository.save(commune);
        }
        return communeRepository.saveAndFlush(commune);
    }

    @Override
    public Commune findById(int id) {
        return communeRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        communeRepository.deleteById(id);
    }
}
