package app.controllers;

import app.entities.Seat;
import app.enums.CategoryType;
import app.services.CategoryService;
import app.services.SeatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = {"/sqlQuery/create-seat-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-seat-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SeatControllerIT extends IntegrationTestBase {

    @Autowired
    private SeatService seatService;

    @Autowired
    private CategoryService categoryService;


    @Test
    void shouldSaveSeat() throws Exception {
        Seat seat = new Seat();
        seat.setSeatNumber("1B");
        seat.setIsLockedBack(true);
        seat.setIsNearEmergencyExit(false);
        seat.setCategory(categoryService.findByCategoryType(CategoryType.ECONOMY));

        mockMvc.perform(post("http://localhost:8080/api/seats")
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetSeatById() throws Exception {
        long id = 1;
        mockMvc.perform(get("http://localhost:8080/api/seats/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(seatService.findById(id))));
    }

    @Test
    void getNotExistedSeat() throws Exception {
        long id = 100;
        mockMvc.perform(get("http://localhost:8080/api/seats/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldEditSeat() throws Exception {
        Seat seat = seatService.findById(1);
        seat.setSeatNumber("1B");
        seat.setIsLockedBack(false);
        seat.setIsNearEmergencyExit(true);
        long id = seat.getId();

        mockMvc.perform(patch("http://localhost:8080/api/seats/{id}", id)
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void editNotExistedSeat() throws Exception {
        long id = 100;
        mockMvc.perform(patch("http://localhost:8080/api/seats/{id}", id)
                        .content(objectMapper.writeValueAsString(seatService.findById(100)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetValidError() throws Exception {
        Seat seat = new Seat();
        seat.setSeatNumber("1");

        mockMvc.perform(post("http://localhost:8080/api/seats")
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        seat.setId(1);
        long id = seat.getId();
        mockMvc.perform(patch("http://localhost:8080/api/seats/{id}", id)
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
