package app.controllers.rest;

import app.controllers.api.rest.PassengerRestApi;
import app.dto.account.PassengerDTO;
import app.entities.account.Passenger;
import app.services.interfaces.PassengerService;
import app.util.mappers.PassengerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PassengerRestController implements PassengerRestApi {

    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @Override
    public ResponseEntity<Page<PassengerDTO>> getAll(Integer page, Integer size) {
        Page<Passenger> passengerPage = passengerService.findAll(page, size);
        if (passengerPage == null) {
            log.error("getAll: Passengers not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAll: find all passengers");
        List<PassengerDTO> passengerDTOS = passengerPage.stream().map(PassengerDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(new PageImpl<>(passengerDTOS, PageRequest.of(page, size), passengerPage.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerDTO> getById(Long id) {
        log.info("getById: get passenger by ID = {}", id);
        Optional<Passenger> passenger = passengerService.findById(id);

        if (passenger.isEmpty()) {
            log.error("getById: passenger with this id={} doesnt exist", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(new PassengerDTO(passenger.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<PassengerDTO>> filterPassengerByKeyword(Pageable pageable, String firstName, String lastName, String email, String serialNumberPassport) {
        Page<Passenger> passengers;
        if (firstName == null && lastName == null && email == null && serialNumberPassport == null) {
            passengers = passengerService.findAll(pageable);
            log.info("getAll: get all Passenger");
            log.info(passengers.toString());
        } else {
            log.info("filter: filter Passenger by firstname or lastname or email or serialNumberPassport");
            passengers = passengerService.filterPassengerByKeyword(pageable, firstName, lastName, email, serialNumberPassport);
            log.info(passengers.toString());
        }
        log.info("passenger пустой: " + passengers.isEmpty());
        if (passengers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passengers.map(entity -> {
            PassengerDTO dto = passengerMapper.convertToPassengerDTO(entity);
            log.info(String.valueOf(dto));
            return dto;
        }), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerDTO> create(PassengerDTO passengerDTO) {
        if (passengerDTO.getId() != null) {
            log.error("create: passenger already exist in database");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("create: new passenger added");
        return new ResponseEntity<>(new PassengerDTO(passengerService.save(passengerMapper.convertToPassengerEntity(passengerDTO))),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PassengerDTO> update(Long id, PassengerDTO passengerDTO) {
        passengerDTO.setId(id);
        log.info("update: passenger={}", passengerDTO);
        return new ResponseEntity<>(new PassengerDTO(passengerService.update(passengerMapper.convertToPassengerEntity(passengerDTO))),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        log.info("delete: passenger with id={} deleted", id);
        passengerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}