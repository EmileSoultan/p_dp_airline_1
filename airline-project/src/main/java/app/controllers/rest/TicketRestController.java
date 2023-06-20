package app.controllers.rest;


import app.controllers.api.rest.TicketRestApi;
import app.dto.TicketDTO;
import app.entities.Flight;
import app.entities.Ticket;
import app.entities.account.Passenger;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import app.services.interfaces.TicketService;
import app.util.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TicketRestController implements TicketRestApi {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Override
    public ResponseEntity<List<TicketDTO>> getAll(Pageable pageable) {
        log.info("getAll: get all Tickets");
        var tickets = ticketService.findAll(pageable);
        return tickets.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(tickets.getContent().stream().map(TicketDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TicketDTO> getByNumber(String ticketNumber) {
        log.info("getByNumber: Ticket by ticketNumber = {}", ticketNumber);
        Ticket ticket = ticketService.findTicketByTicketNumber(ticketNumber);
        return ticket != null
                ? new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<TicketDTO> create(TicketDTO ticketDTO) {
        log.info("create: new Ticket = {}", ticketDTO);
        if (!ticketService.isTicketNumberUnique(ticketDTO.getTicketNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket number must be unique");
        }
        passengerService.findById(ticketDTO.getPassengerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid passengerId"));
        Flight flight = flightService.getById(ticketDTO.getFlightId());
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid flightId");
        }
        Ticket savedTicket = ticketService.saveTicket(ticketMapper.convertToTicketEntity(ticketDTO));
        ticketDTO.setId(savedTicket.getId());
        return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(Long id, TicketDTO ticketDTO) {
        log.info("update: Ticket with id = {}", id);
        ticketDTO.setId(id);
        Ticket ticket = ticketService.updateTicket(id, ticketMapper.convertToTicketEntity(ticketDTO));
        return new ResponseEntity<>(new TicketDTO(ticket), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        try {
            ticketService.deleteTicketById(id);
            log.info("delete: Ticket. id={}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("delete: Ticket with id={} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}