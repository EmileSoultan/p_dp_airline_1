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

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FlightSeatRestController implements FlightSeatRestApi {

    private final FlightSeatServiceImpl flightSeatService;
    private final FlightSeatMapper flightSeatMapper;

    @Override
    public ResponseEntity<Page<FlightSeatDTO>> getAllByFlightId(
            Pageable pageable,
            Long flightId,
            Boolean isSold) {
        Page<FlightSeat> result = null;
        if (isSold != null && !isSold) {
            log.info("getAllByFlightId: get not sold FlightSeats by id={}", flightId);
            result = flightSeatService.findNotSoldById(flightId, pageable);
        } else {
            log.info("getAllByFlightId: get FlightSeats by flightId. flightId={}", flightId);
            result = flightSeatService.findByFlightId(flightId, pageable);
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
        return (flightSeatService.findById(id) == null) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(new FlightSeatDTO(flightSeatService.findById(id)));
    }

    @Override
    public ResponseEntity<FlightSeatDTO> getCheapestByFlightIdAndSeatCategory(Long flightID, CategoryType category) {
        log.info("getCheapestByFlightIdAndSeatCategory: get FlightSeats by flight ID = {} and seat category = {}", flightID, category);
        FlightSeat flightSeat = flightSeatService.getCheapestFlightSeatsByFlightIdAndSeatCategory(flightID, category);
        if (flightSeat == null) {
            log.error("getCheapestByFlightIdAndSeatCategory: FlightSeats with flightID = {} or seat category = {} not found", flightID, category);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new FlightSeatDTO(flightSeat), HttpStatus.OK);
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
        if (flightSeatService.findById(id) == null) {
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