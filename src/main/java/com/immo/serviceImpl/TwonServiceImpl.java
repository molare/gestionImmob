package com.immo.serviceImpl;

import com.immo.entities.Twon;
import com.immo.repositories.TwonRepository;
import com.immo.service.TwonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("twonService")
public class TwonServiceImpl implements TwonService {
    @Autowired
    private TwonRepository twonRepository;

    @Override
    public List<Twon> getAll() {
        return twonRepository.findAll();
    }

    @Override
    public Twon add(Twon twon) {
        return twonRepository.save(twon);
    }

    @Override
    public Twon update(Twon twon) {
        if(twon.getId() ==0){
            return twonRepository.save(twon);
        }
        return twonRepository.saveAndFlush(twon);
    }

    @Override
    public Twon findById(int id) {
        return twonRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        twonRepository.deleteById(id);
    }
}
