package com.example.countryservice;

import com.example.countryservice.modal.Country;
import com.example.countryservice.repository.CountryRepository;
import com.example.countryservice.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTest.class})
public class ServiceMockitoTest {
    @Mock
    CountryRepository countryRepository;
    public List<Country> countryList;
    @InjectMocks
    CountryService countryService;

    @Test
    @Order(1)
    public void testGetAllCountries(){
        countryList = new ArrayList<>();
        countryList.add(new Country(1, "UK", "London"));
        countryList.add(new Country(2, "USA", "Washington"));
        when(countryRepository.findAll()).thenReturn(countryList);
        assertEquals(2, countryService.getCountries().size());
    }
    @Test
    @Order(2)
    public void testGetCountryById(){
        countryList = new ArrayList<>();
        countryList.add(new Country(1, "UK", "London"));
        countryList.add(new Country(2, "USA", "Washington"));
        int countryId = 1;

        when(countryRepository.findById(countryId)).thenReturn(Optional.ofNullable(countryList.get(0)));
        assertEquals(countryId, countryService.getCountryById(countryId).getId());

    }
    @Test
    @Order(3)
    public void testGetCountryByName(){
        countryList = new ArrayList<>();
        countryList.add(new Country(1, "UK", "London"));
        countryList.add(new Country(2, "USA", "Washington"));
        String countryName = "UK";

        when(countryRepository.findByName(countryName)).thenReturn(Optional.ofNullable(countryList.get(0)));
        assertEquals(countryName, countryService.getCountryByName(countryName).getName());

    }
    @Test
    @Order(4)
    public void testAddCountry(){
        Country country = new Country(3, "DE", "Berlin");

        when(countryRepository.save(country)).thenReturn(country);

        Country countryToSave = countryService.addCountry(country);

        assertEquals(country, countryToSave);
        assertEquals(country.getId(), countryToSave.getId());
        assertEquals(country.getName(), countryToSave.getName());
        assertEquals(country.getCapital(), countryToSave.getCapital());
    }
    @Test
    @Order(5)
    public void testUpdateCountry(){
        Country country = new Country(3, "TR", "Istanbul");

        when(countryRepository.findById(country.getId())).thenReturn(Optional.of(country));
        country.setCapital("Ankara");
        when(countryRepository.save(country)).thenReturn(country);


        Country countryToSave = countryService.updateCountry(country.getId(), country);
        assertEquals(country, countryToSave);
        assertEquals(country.getId(), countryToSave.getId());
        assertEquals(country.getName(), countryToSave.getName());
        assertEquals(country.getCapital(), countryToSave.getCapital());
    }
    @Test
    @Order(6)
    public void testDeleteCountry(){
        int id = 3;
        Country country = new Country(id, "TR", "Istanbul");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        countryService.deleteCountry(id);
        verify(countryRepository, times(1)).delete(country);
    }
}
