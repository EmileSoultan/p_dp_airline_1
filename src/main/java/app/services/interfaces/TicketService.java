package app.services.interfaces;

import app.entities.Ticket;

public interface TicketService {
    Ticket findTicketByTicketNumber(String bookingNumber);

    void deleteTicketById(Long id);

    void saveTicket(Ticket ticket);

    void updateTicket(Ticket ticket);
}
