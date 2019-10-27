package com.immo.serviceImpl;

import com.immo.entities.Locater;
import com.immo.repositories.LocaterRepository;
import com.immo.service.LocaterService;
import com.immo.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 09/10/2019.
 */
@Service("locaterService")
public class LocaterServiceImpl implements LocaterService {

    @Autowired
    private LocaterRepository locaterRepository;

    @Override
    public List<Locater> getAll() {
        return locaterRepository.findAll();
    }

    @Override
    public Locater add(Locater locater) {
        return locaterRepository.save(locater);
    }

    @Override
    public Locater update(Locater locater) {
        if(locater.getId()==0){
            return locaterRepository.save(locater);
        }
        return locaterRepository.saveAndFlush(locater);
    }

    @Override
    public Locater findById(int id) {
        return locaterRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        locaterRepository.deleteById(id);
    }
}
