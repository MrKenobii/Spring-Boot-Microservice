package com.example.countryservice.controller;

import com.example.countryservice.dto.DeleteResponse;
import com.example.countryservice.modal.Country;
import com.example.countryservice.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @GetMapping
    public ResponseEntity<List<Country>> getCountries(){

       return new ResponseEntity<>(this.countryService.getCountries(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable int id){
        try {
            return new ResponseEntity<>(this.countryService.getCountryById(id), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/country-name")
    public ResponseEntity<?> getCountryByName(@RequestParam(value = "name") String name){
        try {
            return new ResponseEntity<>(this.countryService.getCountryByName(name), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCountry(@RequestBody Country country){
        try {
            return new ResponseEntity<>(this.countryService.addCountry(country), HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable int id, @RequestBody Country country){
        try {
            return new ResponseEntity<>(this.countryService.updateCountry(id, country), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable int id){
        try {
            return new ResponseEntity<>(this.countryService.deleteCountry(id), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
