package app.services;

import app.dto.account.PassengerDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Transactional
    public Passenger update(Passenger passenger) {
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        passenger.setAnswerQuestion(encoder.encode(passenger.getAnswerQuestion()));
        return passengerRepository.save(passenger);

    }

    @Override
    @Transactional
    public Passenger update(PassengerDTO passengerDTO) {
        var passenger = findById(passengerDTO.getId()).orElseThrow();

        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setBirthDate(passengerDTO.getBirthDate());
        passenger.setPhoneNumber(passengerDTO.getPhoneNumber());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setSecurityQuestion(passengerDTO.getSecurityQuestion());
        passenger.setPassword(encoder.encode(passengerDTO.getPassword()));
        passenger.setAnswerQuestion(encoder.encode(passengerDTO.getAnswerQuestion()));

        if (passengerDTO.getPassport() != null) {
            passenger.setPassport(passengerDTO.getPassport());
        }

        if (passengerDTO.getRoles() != null) {
            passenger.setRoles(passengerDTO.getRoles());
        }

        return passengerRepository.save(passenger);
    }

    @Override
    public Page<Passenger> findAllByKeyword(Pageable pageable, String firstName, String lastName, String email, String serialNumberPassport) {
        if(firstName != null) {
            return passengerRepository.findAllByFirstName(pageable, firstName);
        }
        if(lastName != null) {
            return passengerRepository.findByLastName(pageable, lastName);
        }
        if(email != null) {
            return passengerRepository.findByEmail(pageable, email);
        }
        if(serialNumberPassport != null) {
            return passengerRepository.findByPassportSerialNumber(pageable, serialNumberPassport);
        }
        return passengerRepository.findAll(pageable);
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
    public Page<Passenger> findAll(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    @Override
    public Page<Passenger> findAll(int page, int size) {
        return passengerRepository.findAll(PageRequest.of(page, size));
    }

}
