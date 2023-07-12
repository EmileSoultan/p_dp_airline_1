package app.controllers;

import app.dto.PassengerDTO;
import app.entities.Passport;
import app.entities.Passenger;
import app.enums.Gender;
import app.services.interfaces.PassengerService;
import app.services.interfaces.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PassengerRestControllerIT extends IntegrationTestBase {
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private RoleService roleService;

    @Test
    @DisplayName("Get all passengers with pagination")
    void shouldGetAllPassengers() throws Exception {
        int page = 0;
        int size = 10;

        Page<Passenger>  passengerPage = passengerService.findAll(page, size);

        this.mockMvc.perform(get("http://localhost:8080/api/passengers"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(
                                new PageImpl<>(
                                        passengerPage.stream().map(PassengerDTO::new).collect(Collectors.toList()),
                                        PageRequest.of(page, size),
                                        passengerPage.getTotalElements())
                        )
                    )
                );
    }

    @Test
    @DisplayName("Get passenger by ID")
    void shouldGetPassengerById() throws Exception {
        long id = 4L;
        mockMvc.perform(
                        get("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(new PassengerDTO(passengerService.findById(id).get())))
                );

    }

    @Test
    @DisplayName("Post new passenger")
    void shouldAddNewPassenger() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setFirstName("Petr");
        passengerDTO.setLastName("Petrov");
        passengerDTO.setBirthDate(LocalDate.of(2023, 3, 23));
        passengerDTO.setPhoneNumber("79222222222");
        passengerDTO.setEmail("petrov@mail.ru");
        passengerDTO.setPassword("passwordIsGood?1@");
        passengerDTO.setSecurityQuestion("securityQuestion");
        passengerDTO.setAnswerQuestion("securityQuestion");
        passengerDTO.setRoles(Set.of(roleService.getRoleByName("ROLE_PASSENGER")));
        passengerDTO.setPassport(new Passport("Petr", Gender.MALE, "3333 123456", LocalDate.of(2006, 3, 30), "Russia"));

        mockMvc.perform(
                        post("http://localhost:8080/api/passengers")
                                .content(objectMapper.writeValueAsString(passengerDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Post exist passenger")
    void shouldAddExistPassenger() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId(4L);

        mockMvc.perform(
                        post("http://localhost:8080/api/passengers")
                                .content(objectMapper.writeValueAsString(passengerDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete passenger by ID and check passenger with deleted ID")
    void shouldDeletePassenger() throws Exception {
        long id = 4L;
        mockMvc.perform(delete("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8080/api/passengers/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update passenger")
    void shouldUpdatePassenger() throws Exception {
        long id = 4L;
        PassengerDTO passengerDTO = new PassengerDTO(passengerService.findById(4L).get());
        passengerDTO.setFirstName("Klark");

        mockMvc.perform(put("http://localhost:8080/api/passengers/{id}", id)
                        .content(objectMapper.writeValueAsString(passengerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Filter passenger by FirstName")
    void shouldShowPassengerByFirstName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "Ivan";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                .param("firstName", firstName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by LastName")
    void shouldShowPassengerByLastName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "Ivanov";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("lastName", lastName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by Email")
    void shouldShowPassengerByEmail() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "passenger@mail.ru";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("email", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by PassportSerialNumber")
    void shouldShowPassengerByPassportSerialNumber() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "2222 222222";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("passportSerialNumber", passportSerialNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by FirstName no parameter")
    void shouldShowAllPassengerIfNoParametrFirstName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("firstName", firstName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by LastName no parameter")
    void shouldShowAllPassengerIfNoParametrLastName() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("lastName", lastName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by email no parameter")
    void shouldShowAllPassengerIfNoParametrEmail() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("email", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }
    @Test
    @DisplayName("Filter passenger by passportSerialNumber no parameter")
    void shouldShowAllPassengerIfNoParametrPassportSerialNumber() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        String firstName = "";
        String lastName = "";
        String email = "";
        String passportSerialNumber = "";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("passportSerialNumber", passportSerialNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(passengerService.findAllByKeyword(pageable, firstName, lastName, email, passportSerialNumber))));
    }

    @Test
    @DisplayName("Filter passenger by FirstName not found in database")
    void shouldShowPassengerByFirstNameNotFoundInDatabase() throws Exception {
        String firstName = "aaa";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("firstName", firstName))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter passenger by lastName not found in database")
    void shouldShowPassengerByLastNameNotFoundInDatabase() throws Exception {
        String lastName = "aaa";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("lastName", lastName))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter passenger by email not found in database")
    void shouldShowPassengerByEmailNotFoundInDatabase() throws Exception {
        String email = "aaa@aaa.com";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("email", email))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Filter passenger by FirstName not found in database")
    void shouldShowPassengerByPassportSerialNumberNotFoundInDatabase() throws Exception {
        String serialNumberPassport = "7777 777777";
        mockMvc.perform(get("http://localhost:8080/api/passengers/filter")
                        .param("serialNumberPassport", serialNumberPassport))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
