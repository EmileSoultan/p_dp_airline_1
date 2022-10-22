package app.services;

import app.entities.Ticket;
import app.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket findTicketByBookingNumber(String bookingNumber) {
        return ticketRepository.findByBookingNumberContainingIgnoreCase(bookingNumber);
    }

    @Override
    @Transactional
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void updateTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
