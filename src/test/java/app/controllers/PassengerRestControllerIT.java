package app.controllers;

import app.entities.user.Passenger;
import app.services.interfaces.PassengerService;
import app.services.interfaces.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Set;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PassengerRestControllerIT extends IntegrationTestBase {
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private RoleService roleService;

    @Test
    void shouldGetAllPassengers() throws Exception {
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPassengerById() throws Exception {
        Long id = 4L;
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findById(id))));
    }

    @Test
    void shouldGetPassengerByFullName() throws Exception {
        String passengerFullName = "Ivanov Ivan Jovanovich";
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/fullName/{passengerFullName}", passengerFullName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findByFullName(passengerFullName))));
    }

    @Test
    void shouldGetPassengerByLastName() throws Exception {
        String passengerLastName = "Ivanov";
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/lastName/{passengerLastName}", passengerLastName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findByLastName(passengerLastName))));
    }

    @Test
    void shouldGetPassengerByAnyName() throws Exception {
        String passengerAnyName = "Jovanovich";
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/lastName/{passengerLastName}", passengerAnyName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findByLastName(passengerAnyName))));
    }

    @Test
    void shouldGetPassengerByPassport() throws Exception {
        String serialNumber = "2222 222222";
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/passport/{serialNumber}", serialNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findBySerialNumberPassport(serialNumber))));
    }

    @Test
    void shouldGetNotExistedPassenger() throws Exception {
        Long id = 5L;
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPostNewPassenger() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Test");
        passenger.setLastName("Test");
        passenger.setSecurityQuestion("Test");
        passenger.setAnswerQuestion("Test");
        passenger.setBirthDate(LocalDate.of(2000, 1, 1));
        passenger.setEmail("test2@mail.ru");
        passenger.setPassword("Test123@");
        passenger.setPhoneNumber("79267895643");
        passenger.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        mockMvc.perform(post("http://localhost:8080/api/passengers")
                        .content(objectMapper.writeValueAsString(passenger))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeletePassengerById() throws Exception {
        Long id = 4L;
        mockMvc.perform(delete("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePassenger() throws Exception {
        Long id = 4L;
        Passenger passenger = passengerService.findById(id);
        passenger.setEmail("test@mail.ru");
        mockMvc.perform(patch("http://localhost:8080/api/passengers/{id}", id)
                        .content(objectMapper.writeValueAsString(passenger))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.ru"));
    }
}
