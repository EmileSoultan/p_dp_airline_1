package app.services;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.entities.Seat;
import app.repositories.FlightRepository;
import app.repositories.FlightSeatRepository;
import app.services.interfaces.FlightSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class FlightSeatServiceImpl implements FlightSeatService {

    private FlightSeatRepository flightSeatRepository;
    private FlightRepository flightRepository;

    @Autowired
    public FlightSeatServiceImpl(FlightSeatRepository flightSeatRepository, FlightRepository flightRepository) {
        this.flightSeatRepository = flightSeatRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public Set<FlightSeat> findAll() {
        Set<FlightSeat> flightSeatSet = new HashSet<>();
        flightSeatRepository.findAll().forEach(flightSeat -> flightSeatSet.add(flightSeat));
        return flightSeatSet;
    }

    @Override
    public FlightSeat findById(Long id) {
        return flightSeatRepository.findById(id).orElse(null);
    }

    @Override
    public Set<FlightSeat> findByFlightNumber(String flightNumber) {
        Set<FlightSeat> flightSeatSet = new HashSet<>();
        flightSeatRepository.findFlightSeatByFlight(flightRepository.getByCode(flightNumber))
                .forEach(flightSeat -> flightSeatSet.add(flightSeat));
        return flightSeatSet;
    }

    @Override
    @Transactional
    public Set<FlightSeat> addFlightSeatsByFlightNumber(String flightNumber) {
        Set<FlightSeat> seatsForAdd = new HashSet<>();
        Set<FlightSeat> allFlightSeats = findAll();
        Flight flight = flightRepository.getByCode(flightNumber);
        if (flight != null) {
            Set<Seat> seatsAircraft = flight.getAircraft().getSeatSet();

            for (Seat s : seatsAircraft) {
                FlightSeat flightSeat = new FlightSeat();
                flightSeat.setSeat(s);
                flightSeat.setFlight(flight);
                if (allFlightSeats.contains(flightSeat)) {
                    continue;
                }
                flightSeat.setFare(0);
                flightSeat.setIsSold(false);
                flightSeat.setIsRegistered(false);
                seatsForAdd.add(flightSeat);
            }


            for (FlightSeat f : seatsForAdd) {
                f = flightSeatRepository.save(f);
            }
        }
        return seatsForAdd;
    }

    @Override
    @Transactional
    public FlightSeat saveFlightSeat(FlightSeat flightSeat) {
        return flightSeatRepository.save(flightSeat);
    }
}
