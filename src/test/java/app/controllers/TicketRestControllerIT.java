package app.controllers;

import app.entities.Ticket;
import app.services.interfaces.PassengerService;
import app.services.interfaces.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql(value = {"/sqlQuery/create-ticket-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TicketRestControllerIT extends IntegrationTestBase {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PassengerService passengerService;


    @Test
    void createTicket_test() throws Exception {
        Ticket newTicket = ticketService.findTicketByTicketNumber("ZX-3333");
        newTicket.setTicketNumber("SJ-9346");
        mockMvc.perform(post("http://localhost:8080/api/ticket")
                        .content(objectMapper.writeValueAsString(newTicket))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void showTicketByBookingNumber_test() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/ticket/ticket")
                        .param("ticketNumber", "SD-2222"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateTicket_test() throws Exception {
        Ticket updatedTicket = ticketService.findTicketByTicketNumber("ZX-3333");
        Long ticketId = updatedTicket.getId();
        updatedTicket.setPassenger(passengerService.findById(4L));
        mockMvc.perform(patch("http://localhost:8080/api/ticket/{id}", ticketId)
                        .content(
                                objectMapper.writeValueAsString(updatedTicket)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedTicket)));
    }

    @Test
    void deleteTicket_test() throws Exception {
        Long id = 2L;
        mockMvc.perform(delete("http://localhost:8080/api/ticket/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
