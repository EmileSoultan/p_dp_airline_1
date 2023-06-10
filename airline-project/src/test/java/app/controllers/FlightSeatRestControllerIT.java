package app.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql({"/sqlQuery/create-aircraftSeat-and-flight-before.sql"})
class FlightSeatRestControllerIT extends IntegrationTestBase {

    @Test
    void shouldGenerateFlightSeatsForFlightIdempotent() throws Exception{
        String flightId = "1";
        mockMvc.perform(post("http://localhost:8080/api/flight-seats/all-flight-seats/{flightId}", flightId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/flight-seats/all-flight-seats/{flightId}", flightId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
