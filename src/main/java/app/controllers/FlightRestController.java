package app.controllers;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.enums.FlightStatus;
import app.services.FlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flight")
@Api("Flight API")
@Slf4j
public class FlightRestController {

    private final FlightService flightService;

    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get flight by id", tags = "Flight-Rest-Controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight found"),
            @ApiResponse(code = 404, message = "flight not found")
    })
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {

        log.info("methodName: getFlightById - get flight by id. id = {}", id);
        var flight = flightService.getById(id);

        return flight != null
                ? new ResponseEntity<>(flight, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/seats/{id}")
    @ApiOperation(value = "Get free seats on flight", tags = "Flight-Rest-Controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "free seats found"),
            @ApiResponse(code = 204, message = "no data found")
    })
    public ResponseEntity<Set<FlightSeat>> getFreeSeats(@PathVariable Long id) {

        log.info("methodName: getFreeSeats - get get free seats on flight with id = {}", id);
        Set<FlightSeat> seats = flightService.getFreeSeats(id);

        return ResponseEntity.ok(seats);
    }


    @GetMapping("/filter")
    @ApiOperation(value = "Get flights with dates and destinations given by params", tags = "Flight-Rest-Controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "one or more flights found"),
            @ApiResponse(code = 204, message = "No one flight found")
    })
    public ResponseEntity<List<Flight>> getFlightsByFromAndToAndDates(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "date_start", required = false)  String start,
            @RequestParam(name = "date_finish", required = false) String finish) {

        log.info("methodName: getFlightsByFromAndToAndDates - get flights with params");
        var flightsList = flightService.getFlightByDestinationsAndDates(from, to, start, finish);

        return flightsList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(flightsList, HttpStatus.OK);
    }

    @GetMapping("/filter/dates")
    @ApiOperation(value = "Get flight with id and dates given by params", tags = "Flight-Rest-Controller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight found"),
            @ApiResponse(code = 404, message = "flight not found")
    })
    public ResponseEntity<Flight> getFlightByIdAndDates(@RequestParam(name = "id") Long id,
                                                        @RequestParam (name = "date_start") String start,
                                                        @RequestParam(name = "date_finish") String finish) {

        log.info("methodName: getFlightByIdAndDates - get flight by id and dates");
        var flight = flightService.getFlightByIdAndDates(id, start, finish);

        return flight != null
                ? new ResponseEntity<>(flight, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/status")
    @ApiOperation(value = "Get all flight statuses", tags = "Flight-Rest-Controller")
    @ApiResponse(code = 200, message = "Flight statuses found")
    public ResponseEntity<FlightStatus[]> getAllFlightStatus() {
        log.info("methodName: getAllFlightStatus - get all flight statuses");
        return new ResponseEntity<>(flightService.getAllFlights().stream().map(Flight::getFlightStatus)
                .distinct().collect(Collectors.toList()).toArray(FlightStatus[]::new), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create flight", tags = "Flight-Rest-Controller")
    @ApiResponse(code = 201, message = "Flight created")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {

        log.info("methodName: createFlight - create new flight");
        flightService.save(flight);

        return new ResponseEntity<>(flightService.getFlightByCode(flight.getCode()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update flight", tags = "Flight-Rest-Controller")
    @ApiResponse(code = 200, message = "Flight updated")
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight updated, @PathVariable Long id) {

        log.info("methodName: updateFlight - flight with id = {} updated", id);
        flightService.update(updated);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
