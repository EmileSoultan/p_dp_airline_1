package app;

import app.repositories.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@Sql(value = {"/sqlQuery/create-aircraftAndSeat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-aircraftAndSeat-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AircraftControllerIT extends IntegrationTestBase{

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    public void getAllAircraftMethodReturnAircraft() throws Exception {
        mockMvc.perform(
                        get("http://localhost:8080/api/aircraft"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(aircraftRepository.findAll())));
    }
}
