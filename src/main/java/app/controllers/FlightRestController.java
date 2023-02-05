package app.controllers;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.enums.FlightStatus;
import app.services.interfaces.FlightService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "Flight REST")
@Tag(name = "Flight REST", description = "API для операций с рейсами")
@RestController
@RequestMapping("/api/flight")
@Slf4j
public class FlightRestController {

    private final FlightService flightService;

    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Flight by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight found"),
            @ApiResponse(code = 404, message = "flight not found")
    })
    public ResponseEntity<Flight> getFlightById(
            @ApiParam(
                    name = "id",
                    value = "Flight.id"
            )
            @PathVariable Long id) {

        log.info("getFlightById: get flight by id. id = {}", id);
        var flight = flightService.getById(id);

        return flight != null
                ? new ResponseEntity<>(flight, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/seats/{id}")
    @ApiOperation(value = "Get free seats on Flight by it's \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "free seats found"),
            @ApiResponse(code = 204, message = "no data found")
    })
    public ResponseEntity<Set<FlightSeat>> getFreeSeats(
            @ApiParam(
                    name = "id",
                    value = "Flight.id"
            )
            @PathVariable Long id) {

        log.info("getFreeSeats: get get free seats on flight with id = {}", id);
        Set<FlightSeat> seats = flightService.getFreeSeats(id);

        return ResponseEntity.ok(seats);
    }


    @GetMapping("/filter")
    @ApiOperation(value = "Get list of Flight by dates and destinations given as params")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "one or more flights found"),
            @ApiResponse(code = 204, message = "No one flight found")
    })
    public ResponseEntity<List<Flight>> getFlightsByFromAndToAndDates(
            @ApiParam(name = "from", value = "Departure cityName", example = "Москва")
            @RequestParam(name = "from", required = false) String from,
            @ApiParam(name = "to", value = "Arrival cityName", example = "Омск")
            @RequestParam(name = "to", required = false) String to,
            @ApiParam(value = "Departure Data-Time", example = "2022-12-10T15:56:49")
            @RequestParam(name = "date_start", required = false)  String start,
            @ApiParam(value = "Arrival Data-Time", example = "2022-12-10T15:57:49")
            @RequestParam(name = "date_finish", required = false) String finish) {

        log.info("getFlightsByFromAndToAndDates: get flights with params");
        var flightsList = flightService.getFlightByDestinationsAndDates(from, to, start, finish);

        return flightsList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(flightsList, HttpStatus.OK);
    }

    @GetMapping("/filter/dates")
    @ApiOperation(value = "Get Flight by \"id\" and dates given as params")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "flight found"),
            @ApiResponse(code = 404, message = "flight not found")
    })
    public ResponseEntity<Flight> getFlightByIdAndDates(
            @ApiParam(
                    name = "id",
                    value = "Flight.id"
            )
            @RequestParam(name = "id") Long id,
            @ApiParam(
                    value = "Departure Data-Time",
                    example = "2022-12-10T15:56:49"
            )
            @RequestParam (name = "date_start") String start,
            @ApiParam(
                    value = "Arrival Data-Time",
                    example = "2022-12-10T15:57:49"
            )
            @RequestParam(name = "date_finish") String finish) {

        log.info("getFlightByIdAndDates: get flight by id={} and dates from {} to {}", id, start, finish);
        var flight = flightService.getFlightByIdAndDates(id, start, finish);

        return flight != null
                ? new ResponseEntity<>(flight, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/status")
    @ApiOperation(value = "Get all flight statuses")
    @ApiResponse(code = 200, message = "Flight statuses found")
    public ResponseEntity<FlightStatus[]> getAllFlightStatus() {
        log.info("methodName: getAllFlightStatus - get all flight statuses");
        return new ResponseEntity<>(flightService.getAllFlights().stream().map(Flight::getFlightStatus)
                .distinct().collect(Collectors.toList()).toArray(FlightStatus[]::new), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create Flight")
    @ApiResponse(code = 201, message = "Flight created")
    public ResponseEntity<Flight> createFlight(
            @ApiParam(
                    name = "flight",
                    value = "Flight model"
            )
            @RequestBody Flight flight) {

        log.info("methodName: createFlight - create new flight");
        flightService.save(flight);

        return new ResponseEntity<>(flightService.getFlightByCode(flight.getCode()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit Flight")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Flight updated"),
            @ApiResponse(code = 404, message = "Flight not found")
    })
    public ResponseEntity<Flight> updateFlight(
            @ApiParam(
                    name = "id",
                    value = "Flight.id"
            )
            @PathVariable Long id,
            @ApiParam(
                    name = "flight",
                    value = "Flight model"
            )
            @RequestBody Flight updated) {
        var flight = flightService.getById(id);
        if (flight == null) {
            log.error("updateFlight: flight with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("updateFlight: flight with id = {} updated", id);
        flightService.update(id, updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
