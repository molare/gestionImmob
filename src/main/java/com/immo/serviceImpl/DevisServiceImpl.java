package com.immo.serviceImpl;

import com.immo.entities.Devis;
import com.immo.repositories.DevisRepository;
import com.immo.service.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("devisService")
public class DevisServiceImpl implements DevisService {
    @Autowired
    private DevisRepository devisRepository;

    @Override
    public List<Devis> getAll() {
        return devisRepository.findAll();
    }

    @Override
    public Devis add(Devis devis) {
        return devisRepository.save(devis);
    }

    @Override
    public Devis update(Devis devis) {
        if(devis.getId() ==0){
            return devisRepository.save(devis);
        }
        return devisRepository.saveAndFlush(devis);
    }

    @Override
    public Devis findById(int id) {
        return devisRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        devisRepository.deleteById(id);
    }
}
