package com.immo.serviceImpl;


import com.immo.entities.Contrat;
import com.immo.entities.City;
import com.immo.entities.Property;
import com.immo.repositories.ContratRepository;
import com.immo.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("contratService")
@Transactional
public class ContratServiceImpl implements ContratService {
    @Autowired
    private ContratRepository contratRepository;

    @Override
    public List<Contrat> getAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat add(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat update(Contrat contrat) {
        if(contrat.getId() ==0){
            return contratRepository.save(contrat);
        }
        return contratRepository.saveAndFlush(contrat);
    }

    @Override
    public Contrat findById(int id) {
        return contratRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        contratRepository.deleteById(id);
    }

}
