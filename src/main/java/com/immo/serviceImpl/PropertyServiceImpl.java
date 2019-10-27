package com.immo.serviceImpl;

import com.immo.entities.Property;
import com.immo.repositories.PropertyRepository;
import com.immo.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 09/10/2019.
 */
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    @Override
    public Property add(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Property update(Property property) {
        if(property.getId()==0){
            return propertyRepository.save(property);
        }
        return propertyRepository.saveAndFlush(property);
    }

    @Override
    public Property findById(int id) {
        return propertyRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        propertyRepository.deleteById(id);
    }
}
