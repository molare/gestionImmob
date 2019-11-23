package com.immo.serviceImpl;

import com.immo.entities.Country;
import com.immo.repositories.CountryRepository;
import com.immo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by olivier on 02/10/2019.
 */
@Service("countryService")
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country add(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update(Country country) {
        if(country.getId() ==0){
            return countryRepository.save(country);
        }
        return countryRepository.saveAndFlush(country);
    }

    @Override
    public Country findById(int id) {
        return countryRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        countryRepository.deleteById(id);
    }
}
