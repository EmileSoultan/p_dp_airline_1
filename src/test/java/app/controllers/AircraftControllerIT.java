package app.controllers;

import app.entities.Aircraft;
import app.services.interfaces.AircraftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-aircraftCategorySeat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class AircraftControllerIT extends IntegrationTestBase {
    @Autowired
    private AircraftService aircraftService;

    @Test
    void shouldSaveAircraft() throws Exception {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftNumber("412584");
        aircraft.setModel("Boeing 777");
        aircraft.setModelYear(2005);
        aircraft.setFlightRange(2800);
        mockMvc.perform(post("http://localhost:8080/api/aircraft")
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void shouldGetAllAircraft() throws Exception {
        mockMvc.perform(
                        get("http://localhost:8080/api/aircraft"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAircraftById() throws Exception {
        long id = 2;
        mockMvc.perform(get("http://localhost:8080/api/aircraft/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(aircraftService.findById(id))));
    }

    @Test
    void shouldEditById() throws Exception {
        long id = 2;
        Aircraft aircraft = aircraftService.findById(id);
        aircraft.setAircraftNumber("531487");
        aircraft.setModel("Boeing 737");
        aircraft.setModelYear(2001);
        aircraft.setFlightRange(5000);
        mockMvc.perform(patch("http://localhost:8080/api/aircraft/{id}", id)
                        .content(
                                objectMapper.writeValueAsString(aircraft)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(aircraft)));
    }

    @Test
    void shouldDeleteById() throws Exception {
        long id = 2;
        mockMvc.perform(delete("http://localhost:8080/api/aircraft/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/aircraft/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
