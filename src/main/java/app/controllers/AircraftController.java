package app.controllers;

import app.entities.Aircraft;
import app.services.interfaces.AircraftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

@Api(tags = "Aircraft REST")
@Tag(name = "Aircraft REST", description = "API для операций с самолётом(-ами)")
@RestController
@RequestMapping("/api/aircraft")
@Slf4j
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping()
    @ApiOperation(value = "Get list of all Aircraft")
    public ResponseEntity<List<Aircraft>> getAllAircraft(Pageable pageable) {
        log.info("getAllAircraft: get all aircrafts");
        var aircrafts = aircraftService.findAll(pageable);
        return aircrafts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(aircrafts.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Aircraft by it's \"id\"")
    public ResponseEntity<Aircraft> getAircraftById(
            @ApiParam(
                    name = "id",
                    value = "Aircraft.id"
            )
            @PathVariable("id") Long id) {
        Aircraft aircraft = aircraftService.findById(id);
        if (aircraft == null) {
            log.error("getAircraftById: aircraft with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAircraftById: aircraft with id={} returned.", id);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create new Aircraft")
    public ResponseEntity<?> saveAircraft(
            @ApiParam(
                    name = "Aircraft",
                    value = "Aircraft model"
            )
            @RequestBody @Valid Aircraft aircraft) {
        log.info("saveAircraft: new aircraft saved.");
        return new ResponseEntity<>(aircraftService.save(aircraft), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit Aircraft by \"id\"")
    public ResponseEntity<Aircraft> editAircraft(
            @ApiParam(
                    name = "id",
                    value = "Aircraft.id"
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "Aircraft",
                    value = "Aircraft model"
            )
            @RequestBody @Valid Aircraft aircraft) {
//        if (bindingResult.hasErrors()) {
//            log.error("editAircraft: error of editing aircraft with id={} - wrong input values", id);
//            return ResponseEntity.badRequest().build();
//        }
//        if (aircraftService.findById(id) == null) {
//            log.error("editAircraft: aircraft with id={} doesn't exist.", id);
//            return ResponseEntity.notFound().build();
//        }
        aircraft.setId(id);
        log.info("editAircraft: the aircraft with id={} has been edited.", id);
        return ResponseEntity.ok(aircraftService.save(aircraft));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Aircraft by \"id\"")
    public ResponseEntity<String> deleteAircraft(
            @ApiParam(
                    name = "id",
                    value = "Aircraft.id"
            )
            @PathVariable("id") Long id) {
        try {
            aircraftService.delete(id);
            log.info("deleteAircraft: the aircraft with id={} has been deleted.", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("deleteAircraft: error of deleting - aircraft with id={} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
