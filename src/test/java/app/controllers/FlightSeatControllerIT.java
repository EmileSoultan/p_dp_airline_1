package app.controllers;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.services.interfaces.FlightSeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Sql(value = {"/sqlQuery/create-flightSeat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-flightSeat-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FlightSeatControllerIT extends IntegrationTestBase {

    @Autowired
    private FlightSeatService flightSeatService;

    @Test
    @Transactional
    void shouldGetFlightSeatsByFlightNumber() throws Exception {
        String flightNumber = "MSKOMSK";
        mockMvc.perform(
                        get("http://localhost:8080/api/flight_seats/{flightNumber}", flightNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(flightSeatService.findAll())));
    }

    @Test
    @Transactional
    void shouldAddFlightSeatsByFlightNumber() throws Exception {
        String flightNumber = "MSKOMSK";
        Set<FlightSeat> flightSeatSet = flightSeatService.addFlightSeatsByFlightNumber(flightNumber);

        mockMvc.perform(
                        post("http://localhost:8080/api/flight_seats/addByFlightNumber/{flightNumber}", flightNumber)
                                .content(objectMapper.writeValueAsString(flightSeatSet))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    void shouldEditFlightSeatById() throws Exception {
        Long id = (long) 2;
        FlightSeat flightSeat = flightSeatService.findById(id);
        flightSeat.setFare(100);
        flightSeat.setIsSold(false);
        flightSeat.setIsRegistered(false);

        mockMvc.perform(patch("http://localhost:8080/api/flight_seats/{id}", id)
                        .content(objectMapper.writeValueAsString(flightSeat))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
