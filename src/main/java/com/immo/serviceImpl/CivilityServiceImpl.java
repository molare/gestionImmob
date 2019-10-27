package com.immo.serviceImpl;

import com.immo.entities.Civility;
import com.immo.repositories.CivilityRepository;
import com.immo.service.CivilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("civilityService")
public class CivilityServiceImpl implements CivilityService {
    @Autowired
    private CivilityRepository civilityRepository;

    @Override
    public List<Civility> getAll() {
        return civilityRepository.findAll();
    }

    @Override
    public Civility add(Civility civility) {
        return civilityRepository.save(civility);
    }

    @Override
    public Civility update(Civility civility) {
        if(civility.getId() ==0){
            return civilityRepository.save(civility);
        }
        return civilityRepository.saveAndFlush(civility);
    }

    @Override
    public Civility findById(int id) {
        return civilityRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        civilityRepository.deleteById(id);
    }
}
