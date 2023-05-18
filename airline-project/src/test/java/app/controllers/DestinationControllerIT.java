package app.controllers;

import app.dto.DestinationDTO;
import app.entities.Destination;
import app.enums.Airport;
import app.services.interfaces.DestinationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-destination-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DestinationControllerIT extends IntegrationTestBase {

    @Autowired
    private DestinationService destinationService;

    @Test
    void shouldCreateDestination() throws Exception {
        Destination destination = new Destination(4L, Airport.OMS, "Moscow", "Moscow", "+3", "Russia");
        DestinationDTO destinationDTO = new DestinationDTO(destination);
        System.out.println(objectMapper.writeValueAsString(destination));
        mockMvc.perform(post("http://localhost:8080/api/destinations")
                .content(objectMapper.writeValueAsString(destinationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    void shouldShowDestinationByCityName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String city = "Абакан";
        String country = "";
        mockMvc.perform(get("http://localhost:8080/api/destinations")
                .param("cityName", city)
                .param("countryName", country))
            .andExpect(content().json(objectMapper
                .writeValueAsString(destinationService.findDestinationByName(pageable, city, country)
                    .stream().map(DestinationDTO::new).collect(Collectors.toList()))))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void shouldShowDestinationByCountryName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String city = "";
        String country = "Россия";
        mockMvc.perform(get("http://localhost:8080/api/destinations")
                .param("cityName", city)
                .param("countryName", country))
            .andExpect(content().json(objectMapper
                .writeValueAsString(destinationService.findDestinationByName(pageable, city, country)
                    .stream().map(DestinationDTO::new).collect(Collectors.toList()))))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void shouldShowDestinationByPageable() throws Exception {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));
        String city = "";
        String country = "Россия";
        mockMvc.perform(get("http://localhost:8080/api/destinations?page=0&size=3")
                .param("cityName", city)
                .param("countryName", country))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(destinationService.findAll(pageable)
                .stream().map(DestinationDTO::new).collect(Collectors.toList()))));
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/destinations")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }


    @Transactional
    @Test
    void shouldUpdateDestination() throws Exception {
        Long id = 3L;
        mockMvc.perform(patch("http://localhost:8080/api/destinations/{id}", id)
                .content(objectMapper.writeValueAsString(new DestinationDTO
                    (new Destination(3L, Airport.RAT, "Радужный", "Радужный", "+3", "Россия"))))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDestinationById() throws Exception {
        long id = 2;
        mockMvc.perform(delete("http://localhost:8080/api/destinations/{id}", id))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
