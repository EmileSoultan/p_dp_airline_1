package app.controllers.rest;

import app.controllers.api.rest.FlightRestApi;
import app.dto.FlightDTO;
import app.entities.Flight;
import app.enums.FlightStatus;
import app.services.interfaces.FlightService;
import app.util.mappers.FlightMapper;
import app.util.mappers.FlightSeatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FlightRestController implements FlightRestApi {

    private final FlightService flightService;
    private final FlightMapper flightMapper;

    private final FlightSeatMapper flightSeatMapper;


    @Override
    public ResponseEntity<Page<FlightDTO>> getAll(Pageable pageable) {
        Page<FlightDTO> allFlights = flightService.getAllFlights(pageable).map(entity -> {
            FlightDTO dto = flightMapper.convertToFlightDTOEntity(entity);
            return dto;
        });

        log.info("getAll: get all Flights");
        return allFlights.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(allFlights, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FlightDTO>> getAllByFromAndToAndDates(String from,
                                                                     String to,
                                                                     String start,
                                                                     String finish,
                                                                     Pageable pageable) {
        log.info("getAllByFromAndToAndDates: get Flights with params");
        var flightsList = flightService.getFlightByDestinationsAndDates(from, to, start, finish, pageable);
        return flightsList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(flightsList.stream().map(FlightDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FlightDTO> getById(Long id) {
        log.info("getById: get Flight by id. id = {}", id);
        var flight = flightService.getById(id);
        return flight != null
                ? new ResponseEntity<>(new FlightDTO(flight), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<FlightDTO> getByIdAndDates(Long id, String start, String finish) {
        log.info("getByIdAndDates: get Flight by id={} and dates from {} to {}", id, start, finish);
        var flight = flightService.getFlightByIdAndDates(id, start, finish);
        return flight != null
                ? new ResponseEntity<>(new FlightDTO(flight), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<FlightStatus[]> getAllFlightStatus() {
        log.info("getAllFlightStatus: get all Flight Statuses");
        return new ResponseEntity<>(flightService.getAllFlights().stream().map(FlightDTO::new)
                .map(FlightDTO::getFlightStatus)
                .distinct().collect(Collectors.toList()).toArray(FlightStatus[]::new), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Flight> create(FlightDTO flightDTO) {
        log.info("create: create new Flight");
        return new ResponseEntity<>(flightService.save(flightMapper.convertToFlightEntity(flightDTO)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Flight> update(Long id, FlightDTO flightDTO) {
        var flight = flightService.getById(id);
        if (flight == null) {
            log.error("update: Flight with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("update: Flight with id = {} updated", id);
        return new ResponseEntity<>(flightService.update(id, flightMapper.convertToFlightEntity(flightDTO)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        log.info("delete: Flight with id = {}", id);
        try {
            flightService.deleteById(id);
        } catch (Exception e) {
            log.error("delete: error of deleting - Flight with id = {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}