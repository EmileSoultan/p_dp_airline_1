package app.services;

import app.entities.Ticket;
import app.repositories.FlightRepository;
import app.repositories.FlightSeatRepository;
import app.repositories.PassengerRepository;
import app.repositories.TicketRepository;
import app.services.interfaces.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, PassengerRepository passengerRepository, FlightRepository flightRepository, FlightSeatRepository flightSeatRepository) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.flightSeatRepository = flightSeatRepository;
    }

    @Override
    public Ticket findTicketByTicketNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumberContainingIgnoreCase(ticketNumber);
    }

    @Override
    @Transactional
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Ticket saveTicket(Ticket ticket) {
        ticket.setPassenger(passengerRepository.findByEmail(ticket.getPassenger().getEmail()));
        ticket.setFlight(flightRepository.getByCode(ticket.getFlight().getCode()));
        ticket.setFlightSeat(flightSeatRepository
                .findFlightSeatByFlightAndSeat(
                ticket.getFlight().getCode(),
                ticket.getFlightSeat().getSeat().getSeatNumber()
                ).orElse(null));
        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, Ticket ticket) {
        var targetTicket = findTicketByTicketNumber(ticketRepository.getById(id).getTicketNumber());
        targetTicket.setTicketNumber(ticket.getTicketNumber());

        if (ticket.getPassenger() != null && !ticket.getPassenger().equals(targetTicket.getPassenger())) {
            targetTicket.setPassenger(passengerRepository.findByEmail(ticket.getPassenger().getEmail()));
        }
        return ticketRepository.saveAndFlush(targetTicket);
    }
}
