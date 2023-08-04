package app.controllers.rest;

import app.controllers.api.rest.SeatRestApi;
import app.dto.SeatDTO;

import app.exceptions.ViolationOfForeignKeyConstraintException;
import app.services.interfaces.AircraftService;
import app.services.interfaces.SeatService;
import app.util.mappers.SeatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SeatRestController implements SeatRestApi {

    private final SeatService seatService;
    private final AircraftService aircraftService;
    private final SeatMapper seatMapper;

    @Override
    public ResponseEntity<Page<SeatDTO>> getAll(Integer page, Integer size) {
        var seats = seatService.findAll(page, size).map(entity -> {
            return seatMapper.convertToSeatDTOEntity(entity);
        });
        if (!seats.isEmpty()) {
            log.info("getAll: found {} Seats", seats.getSize());
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } else {
            log.info("getAll: Seats not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Page<SeatDTO>> getAllByAircraftId(Pageable pageable, Long aircraftId) {
        var seats = seatService.findByAircraftId(aircraftId, pageable).map(entity -> {
            return seatMapper.convertToSeatDTOEntity(entity);

        });
        if (!seats.isEmpty()) {
            log.info("getAllByAircraftId: found {} Seats with aircraftId = {}", seats.getSize(), aircraftId);
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } else {
            log.info("getAllByAircraftId: Seats not found with aircraftId = {}", aircraftId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<SeatDTO> getById(Long id) {
        var seat = seatService.findById(id);
        if (seat != null) {
            log.info("getById: Seat with id = {}", id);
            return new ResponseEntity<>(new SeatDTO(seat), HttpStatus.OK);
        } else {
            log.info("getById: Seat not found. id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<SeatDTO> create(SeatDTO seatDTO) {
        var seat = seatMapper.convertToSeatEntity(seatDTO);
        seatService.save(seat);
        log.info("create: Seat saved with id= {}", seat.getId());
        return new ResponseEntity<>(new SeatDTO(seatService.findById(seat.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<SeatDTO>> generate(Long aircraftId) {
        if (aircraftService.findById(aircraftId) == null) {
            log.error("generate: Aircraft with id = {} not found", aircraftId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var savedSeats = seatService.generate(aircraftId);
        if (!savedSeats.isEmpty()) {
            log.info("generate: saved {} new Seats with aircraft.id = {}", savedSeats.size(), aircraftId);
            return new ResponseEntity<>(savedSeats, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SeatDTO> update(Long id, SeatDTO seatDTO) {
        if (seatService.findById(id) == null) {
            log.error("Seat not found id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        seatService.editById(id, seatMapper.convertToSeatEntity(seatDTO));
        log.info("update: Seat with id = {} has been edited.", id);
        return new ResponseEntity<>(new SeatDTO(seatService.findById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        try {
            seatService.delete(id);
            log.info("delete: Seat with id={} has been deleted.", id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (ViolationOfForeignKeyConstraintException e) {
            log.error("delete: error of deleting - Seat with id={} is locked by FlightSeat.", id);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        } catch (EmptyResultDataAccessException e) {
            log.error("delete: error of deleting - Seat with id={} not found.", id);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}