package app.services;

import app.entities.account.Passenger;
import app.repositories.PassengerRepository;
import app.repositories.RoleRepository;
import app.services.interfaces.BookingService;
import app.services.interfaces.FlightSeatService;
import app.services.interfaces.PassengerService;
import app.services.interfaces.TicketService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final BookingService bookingService;
    private final TicketService ticketService;
    private final FlightSeatService flightSeatService;

    // FIXME Отрефакторить
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                RoleRepository roleRepository,
                                PasswordEncoder encoder,
                                @Lazy BookingService bookingService, @Lazy TicketService ticketService, @Lazy FlightSeatService flightSeatService) {
        this.passengerRepository = passengerRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.bookingService = bookingService;
        this.ticketService = ticketService;
        this.flightSeatService = flightSeatService;
    }

    @Override
    @Transactional
    public Passenger save(Passenger passenger) {
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        passenger.setAnswerQuestion(encoder.encode(passenger.getAnswerQuestion()));
        passenger.setRoles(Set.of(roleRepository.findByName("ROLE_PASSENGER")));
        return passengerRepository.save(passenger);
    }

    @Override
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Passenger findByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

    @Override
    public Optional<Passenger> findByPassportSerialNumber(String passportSerialNumber) {
        return passengerRepository.findByPassportSerialNumber(passportSerialNumber);
    }

    @Override
    public List<Passenger> findByLastName(String lastName) {
        return passengerRepository.findByLastName(lastName);
    }

    @Override
    public List<Passenger> findByFistName(String firstName) {
        return passengerRepository.findByFirstName(firstName);
    }

    @Override
    public List<Passenger> findByMiddleName(String middleName) {
        return passengerRepository.findByMiddleName(middleName);
    }

    @Override
    public List<Passenger> findByAnyName(String name) {
        String[] arrNames = name.split("\\s+");
        return findPassengersByAnyName(arrNames);
    }

    @Override
    @Transactional
    public Passenger update(Passenger passenger) {
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        passenger.setAnswerQuestion(encoder.encode(passenger.getAnswerQuestion()));
        return passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        flightSeatService.editIsSoldToFalseByFlightSeatId(ticketService.findArrayOfFlightSeatIdByPassengerId(id));
        bookingService.deleteBookingByPassengerId(id);
        ticketService.deleteTicketByPassengerId(id);
        passengerRepository.deleteById(id);
    }

    @Override
    public Page<Passenger> findAll(int page, int size) {
        return passengerRepository.findAll(PageRequest.of(page, size));
    }

    private List<Passenger> findPassengersByAnyName(String[] partsOfName) {
        List<Passenger> passengerListByLastName = passengerRepository.findByLastName(partsOfName[0]);
        List<Passenger> passengerListByFirstName = passengerRepository.findByFirstName(partsOfName[0]);
        List<Passenger> passengerListByMiddleName = passengerRepository.findByMiddleName(partsOfName[0]);

        List<Passenger> resultList = new ArrayList<>();

        resultList.addAll(passengerListByLastName);
        resultList.addAll(passengerListByFirstName);
        resultList.addAll(passengerListByMiddleName);

        return resultList;
    }
}
