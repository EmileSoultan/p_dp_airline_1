package app.controllers;

import app.entities.user.Passenger;
import app.services.interfaces.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Api(tags = "Passenger REST")
@Tag(name = "Passenger REST", description = "API для операций с пассажирами")
@Slf4j
@RestController
@RequestMapping("/api/passengers")
public class PassengerRestController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerRestController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @ApiOperation(value = "Find passenger by Email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/email")
    public ResponseEntity<Passenger> getByEmail(@RequestParam(name = "email") String email) {
        log.info("methodName: getByEmail - find passenger by email. Email={}", email);
        Passenger passenger = passengerService.findByEmail(email);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passenger);
    }

    @ApiOperation(value = "Get list of all Passenger")
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


    @ApiOperation(value = "Get Passenger by it's \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getById(
            @ApiParam(
                    name = "id",
                    value = "User.id",
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


    @ApiOperation(value = "Get list of Passenger by full name")
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


    @ApiOperation(value = "Get list of Passenger by last name")
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


    @ApiOperation(value = "Get list of Passengers by any name, any format of string. " +
            "WARNING: this method loads the system, better use search by last name or full name. " +
            "If you need a search by other parameters, contact the backend developer.")
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


    @ApiOperation(value = "Get Passenger by serialNumberPassport")
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
                    example = "3333 333333",
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


    @ApiOperation(value = "Create new Passenger", notes = "Create method requires in model field \"@type\": \"passenger\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger added"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping()
    public ResponseEntity<Passenger> addPassenger(
            @ApiParam(
                    name = "passenger",
                    value = "Passenger model"
            )
            @RequestBody @Valid Passenger passenger) {
        log.info("methodName: addPassenger - add new passenger. passenger={}", passenger.toString());
        if (passenger.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(passengerService.save(passenger));
    }


    @ApiOperation(value = "Edit Passenger")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger edited"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Passenger> editPassenger(
            @ApiParam(
                    name = "id",
                    value = "User.id",
                    required = true
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "passenger",
                    value = "Passenger model"
            )
            @RequestBody @Valid Passenger passenger) {
        log.info("methodName: editPassenger - edit passenger by id. id={} passenger={}", id, passenger.toString());
        if (passengerService.findById(id) == null ||
                !id.equals(passenger.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(passengerService.update(passenger));
    }


    @ApiOperation(value = "Delete Passenger by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "passenger deleted")
    })
    @DeleteMapping(value = "/{id}")
    public void deletePassenger(
            @ApiParam(
                    name = "id",
                    value = "User.id",
                    required = true
            )
            @PathVariable Long id) {
        log.info("methodName: deletePassenger - delete passenger by id. id={}", id);
        passengerService.deleteById(id);
    }

}
