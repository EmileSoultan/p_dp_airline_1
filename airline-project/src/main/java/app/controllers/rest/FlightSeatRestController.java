package app.controllers.rest;

import app.controllers.api.rest.FlightSeatRestApi;
import app.dto.FlightSeatDTO;
import app.entities.FlightSeat;
import app.enums.CategoryType;
import app.services.FlightSeatServiceImpl;
import app.util.mappers.FlightSeatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FlightSeatRestController implements FlightSeatRestApi {

    private final FlightSeatServiceImpl flightSeatService;
    private final FlightSeatMapper flightSeatMapper;

    @Override
    public ResponseEntity<Page<FlightSeatDTO>> getAll(
            Pageable pageable,
            Optional<Long> flightId,
            Boolean isSold,
            Boolean isRegistered) {
        Page<FlightSeat> result = null;
        if (isSold != null && !isSold && isRegistered != null && !isRegistered) {
            log.info("getAll: get not sold and not registered FlightSeats by id={}", flightId);
            result = flightSeatService.getFreeSeats(pageable, flightId.orElse(null));
        } else if (isSold != null && !isSold) {
            log.info("getAll: get not sold FlightSeats by id={}", flightId);
            result = flightSeatService.findNotSoldById(flightId.orElse(null), pageable);
        } else if (isRegistered != null && !isRegistered) {
            log.info("getAll: get not registered FlightSeat by id={}", flightId);
            result = flightSeatService.findNotRegisteredById(flightId.orElse(null), pageable);
        } else {
            log.info("getAll: get FlightSeats by flightId. flightId={}", flightId);
            result = flightSeatService.findByFlightId(flightId.orElse(null), pageable);
        }
        return (result.isEmpty()) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(result.map(entity -> {
                    FlightSeatDTO dto = flightSeatMapper.convertToFlightSeatDTOEntity(entity);
                    return dto;
                }));
    }


    @Override
    public ResponseEntity<FlightSeatDTO> get(Long id) {
        log.info("get: FlightSeat by id={}", id);
        return (flightSeatService.findById(id).isEmpty()) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(new FlightSeatDTO(flightSeatService.findById(id).get()));
    }

    @Override
    public ResponseEntity<List<FlightSeatDTO>> getCheapestByFlightIdAndSeatCategory(Long flightID, CategoryType category) {
        log.info("getCheapestByFlightIdAndSeatCategory: get FlightSeats by flight ID = {} and seat category = {}", flightID, category);
        List<FlightSeat> flightSeats = flightSeatService.getCheapestFlightSeatsByFlightIdAndSeatCategory(flightID, category);
        List<FlightSeatDTO> flightSeatDTOS = flightSeats.stream().map(FlightSeatDTO::new).collect(Collectors.toList());
        if (flightSeats.isEmpty()) {
            log.error("getCheapestByFlightIdAndSeatCategory: FlightSeats with flightID = {} or seat category = {} not found", flightID, category);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flightSeatDTOS, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Page<FlightSeatDTO>> getFreeSeats(Pageable pageable, Long id) {
        log.info("getFreeSeats: get free seats on Flight with id = {}", id);
        Page<FlightSeatDTO> seats = flightSeatService.getFreeSeats(pageable, id).map(entity -> {
            FlightSeatDTO seatDTO = flightSeatMapper.convertToFlightSeatDTOEntity(entity);
            return seatDTO;
        });
        return ResponseEntity.ok(seats);
    }

    @Override
    public ResponseEntity<Set<FlightSeatDTO>> generate(Long flightId) {
        log.info("generate: FlightSeats by flightId. flightId={}", flightId);
        Set<FlightSeat> flightSeats = flightSeatService.findByFlightId(flightId);
        if (!flightSeats.isEmpty()) {
            return new ResponseEntity<>(flightSeats.stream().map(FlightSeatDTO::new)
                    .collect(Collectors.toSet()), HttpStatus.OK);
        }
        return new ResponseEntity<>(flightSeatService.addFlightSeatsByFlightId(flightId)
                .stream()
                .map(FlightSeatDTO::new)
                .collect(Collectors.toSet()),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FlightSeatDTO> update(Long id, FlightSeatDTO flightSeatDTO) {
        log.info("update: FlightSeat by id={}", id);
        if (flightSeatService.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new FlightSeatDTO(flightSeatService.editFlightSeat(id, flightSeatMapper.convertToFlightSeatEntity(flightSeatDTO))));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        try {
            flightSeatService.deleteById(id);
            log.info("delete: FlightSeat with id={} deleted", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("delete: error while deleting - FlightSeat with id={} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
