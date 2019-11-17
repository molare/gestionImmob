package com.immo.serviceImpl;


import com.immo.entities.City;
import com.immo.entities.Bien;
import com.immo.entities.Property;
import com.immo.repositories.BienRepository;
import com.immo.service.BienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("bienService")
public class BienServiceImpl implements BienService {
    @Autowired
    private BienRepository bienRepository;
    @PersistenceContext
    private EntityManager em;

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

    @Override
    public int countBien() {
        String sql="SELECT COUNT(DISTINCT b.id) AS nb FROM bien b";
        Query query = em.createNativeQuery(sql);
        try{
            return Integer.parseInt(query.getSingleResult()+"");
        }catch (NoResultException ex){
            return 0;
        }

    }
}
