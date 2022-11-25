package app.controllers;

import app.repositories.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(value = {"/sqlQuery/create-aircraftCategorySeat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-aircraftCategorySeat-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AircraftControllerIT extends IntegrationTestBase {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    void getAllAircraftMethodReturnAircraft() throws Exception {
        mockMvc.perform(
                        get("http://localhost:8080/api/aircraft"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(aircraftRepository.findAll())));
    }
}
