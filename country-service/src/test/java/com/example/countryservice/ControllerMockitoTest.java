package com.example.countryservice;

import com.example.countryservice.controller.CountryController;
import com.example.countryservice.modal.Country;
import com.example.countryservice.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> countryList;

    Country country;

    @Test
    @Order(1)
    public void testGetAllCountries(){
        countryList = new ArrayList<>();
        countryList.add(new Country(1, "TR", "Ankara"));
        countryList.add(new Country(2, "DE", "Berlin"));
        countryList.add(new Country(3, "ES", "Madrid"));

        when(countryService.getCountries()).thenReturn(countryList);

        ResponseEntity<List<Country>> countries = countryController.getCountries();

        assertEquals(HttpStatus.OK,countries.getStatusCode());
        assertEquals(countryList.size(), countries.getBody().size());

        for(int i =0; i<countries.getBody().size(); i++){
            assertEquals(countryList.get(i).getId(), countries.getBody().get(i).getId());
            assertEquals(countryList.get(i).getName(), countries.getBody().get(i).getName());
            assertEquals(countryList.get(i).getCapital(), countries.getBody().get(i).getCapital());
        }
    }
    @Test
    @Order(2)
    public void testGetCountryById(){
         int countryId = 2;
         country = new Country(2, "USA", "Washington");

         when(countryService.getCountryById(country.getId())).thenReturn(country);

        ResponseEntity<?> countryById = countryController.getCountryById(countryId);
        if(countryById.getBody() instanceof Country){
            System.out.println("Found");
            assertEquals(HttpStatus.OK, countryById.getStatusCode());

            assertEquals(country.getId(), ((Country) countryById.getBody()).getId());
            assertEquals(country.getName(), ((Country) countryById.getBody()).getName());
            assertEquals(country.getCapital(), ((Country) countryById.getBody()).getCapital());
        }

    }
    @Test
    @Order(3)
    public void testGetCountryByName(){
        String countryName = "USA";
        country = new Country(2, "USA", "Washington");

        when(countryService.getCountryByName(country.getName())).thenReturn(country);

        ResponseEntity<?> countryByName = countryController.getCountryByName(countryName);
        if(countryByName.getBody() instanceof Country){
            System.out.println("Found");
            assertEquals(HttpStatus.OK, countryByName.getStatusCode());

            assertEquals(country.getId(), ((Country) countryByName.getBody()).getId());
            assertEquals(country.getName(), ((Country) countryByName.getBody()).getName());
            assertEquals(country.getCapital(), ((Country) countryByName.getBody()).getCapital());
        }

    }
    @Test
    @Order(4)
    public void testAddCountry(){
        country = new Country(2, "DE", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);

        ResponseEntity<?> responseEntity = countryController.addCountry(country);

        if(responseEntity.getBody() instanceof Country){
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

            assertEquals(country.getId(), ((Country) responseEntity.getBody()).getId());
            assertEquals(country.getName(), ((Country) responseEntity.getBody()).getName());
            assertEquals(country.getCapital(), ((Country) responseEntity.getBody()).getCapital());
        }
    }
    @Test
    @Order(5)
    public void testUpdateCountry(){
        country = new Country(3, "JA", "Tokyo");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(countryId, country)).thenReturn(country);

        ResponseEntity<?> responseEntity = countryController.updateCountry(countryId, country);

        if(responseEntity.getBody() instanceof Country){
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            assertEquals(country.getId(), ((Country) responseEntity.getBody()).getId());
            assertEquals(country.getName(), ((Country) responseEntity.getBody()).getName());
            assertEquals(country.getCapital(), ((Country) responseEntity.getBody()).getCapital());
        }
    }
    @Test
    @Order(6)
    public void testDeleteCountry(){
        country = new Country(3, "JA", "Tokyo");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country);

        ResponseEntity<?> responseEntity = countryController.deleteCountry(countryId);

        if(responseEntity.getBody() instanceof Country){
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            assertEquals(country.getId(), ((Country) responseEntity.getBody()).getId());
            assertEquals(country.getName(), ((Country) responseEntity.getBody()).getName());
            assertEquals(country.getCapital(), ((Country) responseEntity.getBody()).getCapital());
        }
    }
}
