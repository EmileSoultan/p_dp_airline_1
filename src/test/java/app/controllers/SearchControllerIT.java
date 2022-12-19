package app.controllers;


import app.entities.Destination;
import app.entities.search.Search;
import app.services.interfaces.DestinationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-search-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class SearchControllerIT extends IntegrationTestBase {
    @Autowired
    DestinationService destinationService;

    @Test
    void CheckSearchResultNotFound() throws Exception {
        Long id = 200L;
        mockMvc.perform(get("http://localhost:8080/api/search/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void CreateSearchResultCreate() throws Exception {
        Destination from = destinationService.getDestinationById(2L);
        Destination to = destinationService.getDestinationById(4L);

        Search search = new Search(from, to, LocalDate.now(), LocalDate.now(), 1);
        mockMvc.perform(post("http://localhost:8080/api/search")
                        .content(objectMapper.writeValueAsString(search))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void CheckSearchResult() throws Exception {
        Destination from = destinationService.getDestinationById(4L);
        Destination to = destinationService.getDestinationById(2L);

        Search search = new Search(from, to, LocalDate.now(), LocalDate.now(), 1);
        String search_result = mockMvc.perform(post("http://localhost:8080/api/search")
                        .content(objectMapper.writeValueAsString(search))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = Long.valueOf(search_result);

        mockMvc.perform(get("http://localhost:8080/api/search/{id}", id))
                .andExpect(status().isOk());
    }
}
