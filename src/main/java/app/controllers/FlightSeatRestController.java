package app.controllers;

import app.entities.FlightSeat;
import app.services.FlightSeatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@Api(tags = "FlightSeat REST")
@Tag(name= "FlightSeat REST", description = "Операции с посадочными местами на рейс")
@Slf4j
@RestController
@RequestMapping("/api/flight_seats")
public class FlightSeatRestController {

    FlightSeatServiceImpl flightSeatService;

    @Autowired
    public FlightSeatRestController(FlightSeatServiceImpl flightSeatService) {
        this.flightSeatService = flightSeatService;
    }

    @ApiOperation(value = "Get list of FlightSeat by code of Flight")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight seats found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/{flightNumber}")
    public ResponseEntity<Set<FlightSeat>> getFlightSeatsByFlightNumber(
            @ApiParam(
                    name = "flightNumber",
                    value = "Flight.code",
                    required = true
            )
            @PathVariable
            String flightNumber) {
        log.info("methodName: getFlightSeatsByFlightNumber - get flight seats by flightNumber. flightNumber={}", flightNumber);
        return ResponseEntity.ok(flightSeatService.findByFlightNumber(flightNumber));
    }

    @ApiOperation(value = "Add FlightSeat by code of Flight")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight seats added"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/addByFlightNumber/{flightNumber}")
    public ResponseEntity<Set<FlightSeat>> addFlightSeatsByFlightNumber(
            @ApiParam(
                    name = "flightNumber",
                    value = "Flight.code",
                    required = true
            )
            @PathVariable
            String flightNumber) {
        log.info("methodName: addFlightSeatsByFlightNumber - add flight seats by flightNumber. flightNumber={}", flightNumber);
        return new ResponseEntity<>(flightSeatService.addFlightSeatsByFlightNumber(flightNumber), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit FlightSeat by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight seat edited"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<FlightSeat> editFlightSeatById(
            @ApiParam(
                    name = "id",
                    value = "FlightSeat.id",
                    required = true
            )
            @PathVariable Long id,
            @ApiParam(
                    name = "flightSeat",
                    value = "FlightSeat model",
                    required = true
            )
            @RequestBody
            @Valid FlightSeat flightSeat) {
        log.info("methodName: editFlightSeatById - edit flight seat by id. id={}", id);

        if (flightSeatService.findById(id) == null ||
                !id.equals(flightSeat.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(flightSeatService.saveFlightSeat(flightSeat));
    }


}
