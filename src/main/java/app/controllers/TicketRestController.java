package app.controllers;

import app.entities.Ticket;
import app.services.interfaces.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Ticket REST")
@Tag(name = "Ticket REST", description = "API для операций с билетами")
@Slf4j
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketRestController {
    private final TicketService ticketService;

    @ApiOperation(value = "Create new Ticket")
    @ApiResponse(code = 201, message = "Ticket created")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @ApiParam(
                    name = "ticket",
                    value = "Ticket model"
            )
            @RequestBody Ticket ticket) {
        log.info("methodName: createTicket - create new ticket");
        return new ResponseEntity<>(ticketService.saveTicket(ticket), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets Ticket by ticketNumber")
    @ApiResponse(code = 200, message = "Found the ticket")
    @GetMapping("/ticket")
    public ResponseEntity<Ticket> showTicket(
            @ApiParam(
                    name = "ticketNumber",
                    value = "ticketNumber",
                    example = "SD-2222"
            )
            @RequestParam(value = "ticketNumber") String ticketNumber) {
        log.info("methodName: showTicketByTicketNumber - search ticket by ticketNumber");
        Ticket ticket = ticketService.findTicketByTicketNumber(ticketNumber);
        return ticket != null
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Edit Ticket by \"id\"")
    @ApiResponse(code = 200, message = "Ticket has been updated")
    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @ApiParam(
                    name = "id",
                    value = "Ticket.id"
            ) @PathVariable Long id,
            @ApiParam(
                    name = "ticket",
                    value = "Ticket model"
            )
            @RequestBody Ticket ticket) {
        log.info("methodName: updateTicket - update of current ticket");
        ticket.setId(id);
        return new ResponseEntity<>(ticketService.saveTicket(ticket), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Ticket by \"id\"")
    @ApiResponse(code = 200, message = "Ticket has been removed")
    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(
            @ApiParam(
                    name = "id",
                    value = "Ticket.id"
            )
            @PathVariable Long id) {
        log.info("deleteTicket: ticket of current destination. id={}", id);
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
