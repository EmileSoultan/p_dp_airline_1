package app.services.interfaces;

import app.entities.Ticket;
import org.springframework.data.domain.Page;

public interface TicketService {

    Page<Ticket> findAll(int page, int size);

    Ticket findTicketByTicketNumber(String bookingNumber);

    void deleteTicketById(Long id);

    Ticket saveTicket(Ticket ticket);

    Ticket updateTicket(Long id, Ticket updatedTicket);


    long [] findArrayOfFlightSeatIdByPassengerId(long passengerId);

    void deleteTicketByPassengerId(long passengerId);
}
