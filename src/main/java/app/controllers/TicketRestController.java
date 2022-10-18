package app.controllers;

import app.entities.Ticket;
import app.services.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation("Ticket API")
@Slf4j
@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {
    private final TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ApiOperation(value = "Create new ticket", tags = "ticket-rest-controller")
    @ApiResponse(code = 201, message = "Ticket created")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        log.info("methodName: createTicket - create new ticket");
        ticketService.saveTicket(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Gets ticket by bookingNumber", tags = "ticket-rest-controller")
    @ApiResponse(code = 200, message = "Found the ticket")
    @GetMapping("/ticket")
    public ResponseEntity<Ticket> showTicket(
            @RequestParam(value = "bookingNumber") @ApiParam("bookingNumber") String bookingNumber) {
        log.info("methodName: showTicketByBookingNumber - search ticket by bookingNumber");
        Ticket ticket = ticketService.findTicketByBookingNumber(bookingNumber);
        return ticket != null
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Update ticket", tags = "ticket-rest-controller")
    @ApiResponse(code = 200, message = "Ticket has been updated")
    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        log.info("methodName: updateTicket - update of current ticket");
        ticketService.updateTicket(ticket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete ticket", tags = "ticket-rest-controller")
    @ApiResponse(code = 200, message = "Ticket has been removed")
    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable @ApiParam("id") Long id) {
        log.info("methodName: deleteTicket - ticket of current destination");
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
