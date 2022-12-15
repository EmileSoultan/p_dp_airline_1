package app.controllers;

import app.entities.user.Passenger;
import app.services.interfaces.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@Api
@Slf4j
@RestController
@RequestMapping("/api/passengers")
public class PassengerRestController {

    private PassengerService passengerService;


    @Autowired
    public PassengerRestController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }


    @ApiOperation(value = "Find all passengers", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping()
    public ResponseEntity<List<Passenger>> getAll() {
        log.info("methodName: getAll - find all passengers");
        List<Passenger> passengers = passengerService.findAll();
        if (passengers == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengers);
    }


    @ApiOperation(value = "Find passenger by ID", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getById(
            @ApiParam(
                    name = "id",
                    value = "User id",
                    required = true
            )
            @PathVariable Long id) {
        log.info("methodName: getById - find passenger by ID. id={}", id);
        Passenger passenger = passengerService.findById(id);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passenger);
    }


    @ApiOperation(value = "Find passengers by full name",
            tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/fullName/{passengerFullName}")
    public ResponseEntity<List<Passenger>> getByFullName(
            @ApiParam(
                    name = "passengerFullName",
                    value = "Passenger full name\n" +
                            "format {lastName firstName middleName}\n" +
                            "example: \"Иванов Пётр Владимирович\"",
                    required = true
            )
            @PathVariable String passengerFullName) {
        List<Passenger> passengerList = passengerService.findByFullName(passengerFullName);
        if (passengerList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerList);
    }


    @ApiOperation(value = "Find passengers by last name", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/lastName/{passengerLastName}")
    public ResponseEntity<List<Passenger>> getByLastName(
            @ApiParam(
                    name = "passengerLastName",
                    value = "Passenger last name\n" +
                            "example: \"Иванов\" \"Петров\" \"Евдокимов\"",
                    required = true
            )
            @PathVariable String passengerLastName) {
        List<Passenger> passengerList = passengerService.findByLastName(passengerLastName);
        if (passengerList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerList);
    }


    @ApiOperation(value = "Find passengers by any name, any format of string. " +
            "WARNING: this method loads the system, better use search by last name or full name. " +
            "If you need a search by other parameters, contact the backend developer.",
            tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/anyName/{passengerAnyName}")
    public ResponseEntity<List<Passenger>> getByAnyName(
            @ApiParam(
                    name = "passengerAnyName",
                    value = "Passenger any name, custom format\n" +
                            "example: \"Пётр\" \"Петрович Пётр\" \"Пётр Петров Петрович\"",
                    required = true
            )
            @PathVariable String passengerAnyName) {
        List<Passenger> passengerList = passengerService.findByAnyName(passengerAnyName);
        if (passengerList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerList);
    }


    @ApiOperation(value = "Find passenger by PassportSerialNumber", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/passport/{serialNumber}")
    public ResponseEntity<Passenger> getByPassportSerialNumber(
            @ApiParam(
                    name = "serialNumber",
                    value = "Passenger serial and number of passport\n" +
                            "example: \"7777 777777\"",
                    required = true
            )
            @PathVariable String serialNumber) {
        log.info("methodName: getByPassportSerialNumber - find passenger by PassportSerialNumber. serialNumber={}", serialNumber);
        Passenger passenger = passengerService.findBySerialNumberPassport(serialNumber);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerService.findBySerialNumberPassport(serialNumber));
    }


    @ApiOperation(value = "Add new passenger", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger added"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping()
    public ResponseEntity<Passenger> addPassenger(@RequestBody @Valid Passenger passenger) {
        log.info("methodName: addPassenger - add new passenger. passanger={}", passenger.toString());
        if (passenger.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(passengerService.save(passenger));
    }


    @ApiOperation(value = "Edit passenger", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger edited"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Passenger> editPassenger(
            @ApiParam(
                    name = "id",
                    value = "User id",
                    required = true
            )
            @RequestBody @Valid Passenger passenger,
            @PathVariable Long id) {
        log.info("methodName: editPassenger - edit passenger by id. id={} passenger={}", id, passenger.toString());
        if (passengerService.findById(id) == null ||
                !id.equals(passenger.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(passengerService.save(passenger));
    }


    @ApiOperation(value = "Delete passenger", tags = "passenger-rest-controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger deleted")
    })
    @DeleteMapping(value = "/{id}")
    public void deletePassenger(
            @ApiParam(
                    name = "id",
                    value = "User id",
                    required = true
            )
            @PathVariable Long id) {
        log.info("methodName: deletePassenger - delete passenger by id. id={}", id);
        passengerService.deleteById(id);
    }

}
