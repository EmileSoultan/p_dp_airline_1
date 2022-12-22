package app.controllers;

import app.entities.user.AirlineManager;
import app.entities.user.User;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerIT extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(
                        get("http://localhost:8080/api/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userService.getAllUsers())));
    }

    @Test
    void shouldGetUserById() throws Exception {
        Long id = 4L;
        mockMvc.perform(
                        get("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userService.getUserById(id))));
    }

    @Test
    void shouldGetNotExistedUser() throws Exception {
        Long id = 100L;
        mockMvc.perform(
                        get("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPostNewUser() throws Exception {
        AirlineManager airlineManager = new AirlineManager();
        airlineManager.setEmail("manager2@mail.ru");
        airlineManager.setPassword("Test123@");
        airlineManager.setSecurityQuestion("Test");
        airlineManager.setAnswerQuestion("Test");
        airlineManager.setRoles(Set.of(roleService.getRoleByName("ROLE_MANAGER")));
        mockMvc.perform(post("http://localhost:8080/api/user")
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        Long id = 4L;
        mockMvc.perform(delete("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void shouldUpdateUser() throws Exception {
        Long id = 3L;
        User user = userService.getUserById(id).orElseThrow();
        user.setEmail("test@mail.ru");
        mockMvc.perform(patch("http://localhost:8080/api/user/{id}", id)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.ru"));
    }
}
