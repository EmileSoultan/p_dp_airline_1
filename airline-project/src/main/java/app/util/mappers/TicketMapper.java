package app.util.mappers;

import app.dto.TicketDTO;
import app.entities.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    public Ticket convertToTicketEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO.getId());
        ticket.setTicketNumber(ticketDTO.getTicketNumber());
        ticket.setPassenger(ticketDTO.getPassenger());
        ticket.setFlight(ticketDTO.getFlight());
        ticket.setFlightSeat(ticketDTO.getFlightSeat());
        return ticket;
    }
}
