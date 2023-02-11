package com.example.countryservice;

import com.example.countryservice.controller.CountryController;
import com.example.countryservice.modal.Country;
import com.example.countryservice.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.example.countryservice")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTest.class})
public class ControllerMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;
    List<Country> countryList;
    Country country;
    @InjectMocks
    CountryController countryController;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }
    @Test
    @Order(1)
    public void testGetAllCountries(){
        countryList = new ArrayList<>();
        countryList.add(new Country(1, "TR", "Ankara"));
        countryList.add(new Country(2, "UK", "London"));
        countryList.add(new Country(3, "ITA", "Rome"));

        when(countryService.getCountries()).thenReturn(countryList);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/country"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testGetCountryById(){
        country = new Country(2, "UK", "London");
        int countryId = 2;
        when(countryService.getCountryById(countryId)).thenReturn(country);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/country/{id}", countryId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(countryId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(country.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.capital").value(country.getCapital()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(3)
    public void testGetCountryByName(){
        country = new Country(2, "UK", "London");
        String countryName = "UK";
        when(countryService.getCountryByName(countryName)).thenReturn(country);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/country/country-name").param("name", countryName))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(country.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(countryName))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.capital").value(country.getCapital()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void testAddCountry() throws JsonProcessingException {
        country = new Country(2, "UK", "London");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.post("/country").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(5)
    public void testUpdateCountry() throws JsonProcessingException {
        country = new Country(3, "UK", "London");
        int countryId = 3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        country.setName("EN");
        when(countryService.updateCountry(countryId, country)).thenReturn(country);
        when(countryService.getCountryById(countryId)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.put("/country/{id}", countryId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(countryId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(country.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.capital").value(country.getCapital()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(6)
    public void testDeleteCountry(){
        country = new Country(3, "UK", "London");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country);

        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/country/{id}", countryId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
