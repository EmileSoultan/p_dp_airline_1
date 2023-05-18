package app.services.interfaces;

import app.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketService {

    Page<Ticket> findAll(Pageable pageable);

    Ticket findTicketByTicketNumber(String bookingNumber);

    void deleteTicketById(Long id);

    Ticket saveTicket(Ticket ticket);

    Ticket updateTicket(Long id, Ticket updatedTicket);


    long [] findArrayOfFlightSeatIdByPassengerId(long passengerId);

    void deleteTicketByPassengerId(long passengerId);
}
