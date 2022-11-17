package app.controllers;

import app.entities.Destination;
import app.services.DestinationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiOperation("Destination API")
@Slf4j
@RestController
@RequestMapping("/api/destination")
public class DestinationRestController {
    private final DestinationService destinationService;

    public DestinationRestController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @ApiOperation(value = "Create new destination", tags = "destination-rest-controller")
    @ApiResponse(code = 201, message = "Destination created")
    @PostMapping
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        log.info("methodName: createDestination - create new destination");
        destinationService.saveDestination(destination);
        return new ResponseEntity<>(destination, HttpStatus.CREATED);
    }

    @GetMapping()
    @ApiOperation(value = "Get all destinations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "destinations found"),
            @ApiResponse(code = 404, message = "destinations not found")
    })
    public ResponseEntity<List<Destination>> getAllDestination() {

        List<Destination> destinations = destinationService.findAllDestinations();

        if (destinations != null) {
            log.info("getAllDestination: find all destinations");
            return new ResponseEntity<>(destinations, HttpStatus.OK);
        } else {
            log.info("getAllDestination: list destinations is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Gets destinations by city name or country name", tags = "destination-rest-controller")
    @ApiResponse(code = 200, message = "Found the destinations")
    @GetMapping("/filter")
    public ResponseEntity<List<Destination>> showDestinationByCityName(
            @RequestParam(value = "cityName", required = false) @ApiParam("cityName") String cityName,
            @RequestParam(value = "countryName", required = false) @ApiParam("countryName") String countryName) {
        log.info("methodName: showDestinationByCityName - search destination by cityName");
        List<Destination> destination = destinationService.findDestinationByName(cityName, countryName);
        return destination != null
                ? new ResponseEntity<>(destination, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Update destination", tags = "destination-rest-controller")
    @ApiResponse(code = 200, message = "Destination has been updated")
    @PatchMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination) {
        log.info("methodName: updateDestination - update of current destination");
        destinationService.updateDestination(destination);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete destination", tags = "destination-rest-controller")
    @ApiResponse(code = 200, message = "Destination has been removed")
    @DeleteMapping("/{id}")
    public ResponseEntity<Destination> deleteDestination(@PathVariable @ApiParam("id") Long id) {
        log.info("methodName: deleteDestination - delete of current destination");
        destinationService.deleteDestinationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
