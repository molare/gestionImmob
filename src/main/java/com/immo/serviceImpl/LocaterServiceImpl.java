package com.immo.serviceImpl;

import com.immo.entities.Locater;
import com.immo.repositories.LocaterRepository;
import com.immo.service.LocaterService;
import com.immo.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by olivier on 09/10/2019.
 */
@Service("locaterService")
public class LocaterServiceImpl implements LocaterService {

    @Autowired
    private LocaterRepository locaterRepository;
    @PersistenceContext
    private EntityManager em;

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

    @Override
    public int countLocater() {
        String sql="SELECT COUNT(DISTINCT l.id) AS nb FROM locater l";
        Query query = em.createNativeQuery(sql);
        try{
            return Integer.parseInt(query.getSingleResult()+"");
        }catch (NoResultException ex){
            return 0;
        }
    }
}
