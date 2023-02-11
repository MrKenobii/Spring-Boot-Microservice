package com.example.countryservice;

import com.example.countryservice.modal.Country;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerIntegrationTests.class})
public class ControllerIntegrationTests {

    @Test
    @Order(1)
    void getAllCountriesIntegrationTest(){
        String expected = """
                [
                    {
                        "id": 1,
                        "name": "TR",
                        "capital": "Ankara"
                    },
                    {
                        "id": 2,
                        "name": "UK",
                        "capital": "London"
                    },
                    {
                        "id": 3,
                        "name": "USA",
                        "capital": "Washington"
                    }
                ]
                """;
//        String expected = "[\n" +
//                "    {\n" +
//                "        \"id\": 1,\n" +
//                "        \"name\": \"Ankara\",\n" +
//                "        \"capital\": \"TR\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"id\": 2,\n" +
//                "        \"name\": \"London\",\n" +
//                "        \"capital\": \"UK\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"id\": 3,\n" +
//                "        \"name\": \"Washington\",\n" +
//                "        \"capital\": \"USA\"\n" +
//                "    }\n" +
//                "]";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/country", String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void getAllCountryByIdIntegrationTest(){
        String expected = """
                    {
                        "id": 1,
                        "name": "TR",
                        "capital": "Ankara"
                    }
                """;
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/country/1", String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(3)
    void getAllCountryByNameIntegrationTest(){
        String expected = """
                    {
                        "id": 1,
                        "name": "TR",
                        "capital": "Ankara"
                    }
                """;
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/country/country-name?name=TR", String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(4)
    void addCountryIntegrationTest(){
        Country country = new Country(4, "DE", "Berlin");
        String expected = """
                    {
                        "id": 4,
                        "name": "DE",
                        "capital": "Berlin"
                    }
                """;
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> httpEntity = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/country",HttpMethod.POST ,httpEntity, String.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    @Order(5)
    void updateCountryIntegrationTest(){
        Country country = new Country(3, "JN", "Tokyo");
        String expected = """
                    {
                        "id": 3,
                        "name": "JN",
                        "capital": "Tokyo"
                    }
                """;
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> httpEntity = new HttpEntity<>(country, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/country/3",HttpMethod.PUT, httpEntity, String.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(6)
    void deleteCountryIntegrationTest(){
        Country country = new Country(3, "JN", "Tokyo");
        String expected = """
                    {
                        "message": "Country was deleted",
                        "id": 3
                    }
                """;
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> httpEntity = new HttpEntity<>(country, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/country/3",HttpMethod.DELETE, httpEntity, String.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        try {
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //restTemplate.delete("http://localhost:8080/country/3");
    }
}
