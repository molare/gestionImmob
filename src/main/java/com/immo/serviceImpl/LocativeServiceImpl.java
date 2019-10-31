package com.immo.serviceImpl;


import com.immo.entities.Locative;
import com.immo.entities.City;
import com.immo.entities.Property;
import com.immo.repositories.LocativeRepository;
import com.immo.service.LocativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("locativeService")
@Transactional
public class LocativeServiceImpl implements LocativeService {
    @Autowired
    private LocativeRepository locativeRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Locative> getAll() {
        return locativeRepository.findAll();
    }

    @Override
    public Locative add(Locative locative) {
        return locativeRepository.save(locative);
    }

    @Override
    public Locative update(Locative locative) {
        if(locative.getId() ==0){
            return locativeRepository.save(locative);
        }
        return locativeRepository.saveAndFlush(locative);
    }

    @Override
    public Locative findById(int id) {
        return locativeRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        locativeRepository.deleteById(id);
    }

    @Override
    public List<Locative> findByProperty(Property property) {
        return null;
    }

    @Override
    public List<Locative> findByCity(City city) {
        return null;
    }


    @Override
    public double garanty(int id) {
        String sql="SELECT (lo.amount+lo.charge) AS garanty FROM locative lo\n" +
                "WHERE lo.id ="+id;
        Query query = em.createNativeQuery(sql);
        return Double.parseDouble(query.getSingleResult()+"");
    }

    @Override
    public Locative findByContrat(int id) {
        Locative lo =null;
        try {
            String sql = "SELECT lo.id FROM contrat con, locative lo\n" +
                    "WHERE con.locative_id = lo.id\n" +
                    "AND lo.id="+ id;
            Query query = em.createNativeQuery(sql);
            lo = locativeRepository.findById(Integer.parseInt(query.getSingleResult() + ""));
        }catch (NoResultException ex){
            return null;
        }
        return lo;
    }
}
