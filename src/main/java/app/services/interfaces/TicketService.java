package app.services.interfaces;

import app.entities.Ticket;

public interface TicketService {
    Ticket findTicketByTicketNumber(String bookingNumber);

    void deleteTicketById(Long id);

    Ticket saveTicket(Ticket ticket);

    Ticket updateTicket(Long id, Ticket ticket);
}
