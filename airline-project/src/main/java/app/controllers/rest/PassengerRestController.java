package app.controllers.rest;

import app.controllers.api.rest.PassengerRestApi;
import app.dto.account.PassengerDTO;
import app.entities.account.Passenger;
import app.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<PassengerDTO> getByEmail(String email) {
        log.info("getByEmail: get passenger by email = {}", email);
        Passenger passenger = passengerService.findByEmail(email);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PassengerDTO(passenger), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PassengerDTO>> getByFirstName(String passengerFirstName) {
        log.info("getByFullName: get passenger by firstname = {}", passengerFirstName);
        List<Passenger> passengerList = passengerService.findByFistName(passengerFirstName);
        return passengerList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(passengerList.stream().map(PassengerDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PassengerDTO>> getByLastName(String passengerLastName) {
        log.info("getByLastName: get passengers by lastname = {}", passengerLastName);
        List<Passenger> passengerList = passengerService.findByLastName(passengerLastName);
        return passengerList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(passengerList.stream().map(PassengerDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PassengerDTO>> getByAnyName(String passengerAnyName) {
        log.info("getByAnyName: get passenger by anyName = {}", passengerAnyName);
        List<Passenger> passengerList = passengerService.findByAnyName(passengerAnyName);
        return passengerList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(passengerList.stream().map(PassengerDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerDTO> getByPassportSerialNumber(String serialNumber) {
        log.info("getByPassportSerialNumber: get passenger by PassportSerialNumber. serialNumber={}", serialNumber);
        Optional<Passenger> passenger = passengerService.findByPassportSerialNumber(serialNumber);
        if (passenger.isEmpty()) {
            log.error("getByPassportSerialNumber: passenger with this serial number doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PassengerDTO(passenger.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerDTO> create(PassengerDTO passengerDTO) {
        if (passengerDTO.getId() != null) {
            log.error("create: passenger already exist in database");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("create: new passenger added");
        return new ResponseEntity<>(new PassengerDTO(passengerService.save(passengerDTO.convertToEntity())),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PassengerDTO> update(Long id, PassengerDTO passengerDTO) {
        passengerDTO.setId(id);
        log.info("update: passenger={}", passengerDTO);
        return new ResponseEntity<>(new PassengerDTO(passengerService.update(passengerDTO.convertToEntity())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        log.info("delete: passenger with id={} deleted", id);
        passengerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}