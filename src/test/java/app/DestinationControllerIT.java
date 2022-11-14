package app;

import app.entities.Destination;
import app.enums.Airport;
import app.services.DestinationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = {"/sqlQuery/create-destination-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-destination-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DestinationControllerIT extends IntegrationTestBase {

    @Autowired
    private DestinationService destinationService;

    @Test
    void shouldCreateDestination() throws Exception {
        Destination destination = new Destination(4L, Airport.OMS, "Moscow", "Moscow", "+3", "Russia");
        System.out.println(objectMapper.writeValueAsString(destination));
        mockMvc.perform(post("http://localhost:8080/api/destination")
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldShowDestinationByName() throws Exception {
        String city = "Абакан";
        String country = "";
        mockMvc.perform(get("http://localhost:8080/api/destination")
                        .param("cityName", city)
                        .param("countryName", country))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void shouldUpdateDestination() throws Exception {
        Long id = 3L;
        mockMvc.perform(patch("http://localhost:8080/api/destination/{id}", id)
                        .content(objectMapper.writeValueAsString(new Destination(3L, Airport.RAT, "Радужный", "Радужный", "+3", "Россия")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDestinationById() throws Exception {
        long id = 2;
        mockMvc.perform(delete("http://localhost:8080/api/destination/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
