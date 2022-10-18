package app.services;

import app.entities.Ticket;

public interface TicketService {
    Ticket findTicketByBookingNumber(String bookingNumber);
    void deleteTicketById(Long id);
    void saveTicket(Ticket ticket);
    void updateTicket(Ticket ticket);
}
