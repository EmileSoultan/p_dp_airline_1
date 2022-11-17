package app.controllers;

import app.entities.user.Admin;
import app.entities.user.User;
import app.repositories.RoleRepository;
import app.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = {"/sqlQuery/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sqlQuery/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerIT extends IntegrationTestBase{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

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
        Long id = 2L;
        mockMvc.perform(
                        get("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userService.getUserById(id))));
    }

    @Test
    void shouldGetNotExistedUser() throws Exception {
        Long id = 5L;
        mockMvc.perform(
                        get("http://localhost:8080/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPostNewUser() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("admin2@mail.ru");
        admin.setPassword("admin2");
        admin.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN")));
        System.out.println(objectMapper.writeValueAsString(admin));
        mockMvc.perform(
                        post("http://localhost:8080/api/user")
                                .content(objectMapper.writeValueAsString(admin))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldDeleteUserById() throws Exception {
        Long id = 2L;
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
        User admin = userService.getUserById(2L).orElseThrow();
        admin.setEmail("adminka@mail.ru");
        mockMvc.perform(patch("http://localhost:8080/api/user")
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
