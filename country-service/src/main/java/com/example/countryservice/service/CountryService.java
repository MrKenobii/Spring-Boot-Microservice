package com.example.countryservice.service;

import com.example.countryservice.dto.DeleteResponse;
import com.example.countryservice.modal.Country;
import com.example.countryservice.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getCountries(){
        return this.countryRepository.findAll();
    }
    public Country getCountryById(int id){
       return this.countryRepository.findById(id).orElseThrow(() -> {
           throw new RuntimeException("No country found with id: " + id);
       });
    }
    public Country getCountryByName(String name){
        return this.countryRepository.findByName(name).orElseThrow(() -> {
            throw new RuntimeException("No country found with name " + name);
        });
    }
    public Country addCountry(Country country){
        return this.countryRepository.save(country);
    }
    public Country updateCountry(int id,Country country){
        Country country1 = this.countryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("No Country found with id: " + id);
        });
        country1.setName(country.getName());
        country1.setCapital(country.getCapital());
        return this.countryRepository.save(country1);
    }
    public DeleteResponse deleteCountry(int id){
        Country country = this.countryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("No Country found with id: " + id);
        });
        this.countryRepository.delete(country);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("Country was deleted");
        deleteResponse.setId(id);
        return deleteResponse;
    }
    public int getMaxId(){
        return this.countryRepository.findAll().size() + 1;
    }
}
