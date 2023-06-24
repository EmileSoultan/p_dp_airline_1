package app.controllers;

import app.dto.TimezoneDTO;
import app.entities.Timezone;
import app.services.interfaces.TimezoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-timezone-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class TimezoneRestControllerIT extends IntegrationTestBase {

    @Autowired
    private TimezoneService timezoneService;

    @Test
    @DisplayName("Creating Timezone")
    void shouldCreateNewTimezone() throws Exception {
        TimezoneDTO timezoneDTO = new TimezoneDTO();
        timezoneDTO.setCityName("New-York");
        timezoneDTO.setCountryName("USA");
        timezoneDTO.setGmt("GMT-5");
        timezoneDTO.setGmtWinter("GMT-4");
        mockMvc.perform(post("http://localhost:8080/api/timezones")
                        .content(objectMapper.writeValueAsString(timezoneDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Get all Timezones")
    void shouldGetAllTimezones() throws Exception {
        int page = 0;
        int size = 10;
        Page<Timezone> timezonePage = timezoneService.findAll(page, size);
        mockMvc.perform(get("http://localhost:8080/api/timezones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new PageImpl<>(
                                timezonePage.stream().map(TimezoneDTO::new).collect(Collectors.toList()),
                                PageRequest.of(page, size),
                                timezonePage.getTotalElements())
                )));
    }

    @Test
    @DisplayName("Get Timezone by ID")
    void shouldGetTimezoneById() throws Exception {
        long id = 3L;
        mockMvc.perform(get("http://localhost:8080/api/timezones/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new TimezoneDTO(timezoneService.getTimezoneById(id).get())))
                );
    }

    @Test
    @DisplayName("Delete Timezone by ID")
    void shouldDeleteById() throws Exception {
        Long id = 3L;
        mockMvc.perform(delete("http://localhost:8080/api/timezones/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        mockMvc.perform(get("http://localhost:8080/api/timezones/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update timezone")
    void shouldUpdateTimezone() throws Exception {
        long id = 5L;
        TimezoneDTO timezoneDTO = new TimezoneDTO(timezoneService.getTimezoneById(id).get());
        timezoneDTO.setCountryName("Чехия");
        mockMvc.perform(patch("http://localhost:8080/api/timezones/{id}", id)
                        .content(objectMapper.writeValueAsString(timezoneDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}