package app.controllers;

import app.entities.Aircraft;
import app.services.interfaces.AircraftService;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@Slf4j
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping()
    @ApiOperation(value = "Получение списка всех Aircraft")
    public ResponseEntity<List<Aircraft>> getAllAircraft() {
        log.info("getAllAircraft: all aircraft returned");
        return ResponseEntity.ok(aircraftService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить Aircraft по \"Id\"")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable("id") Long id) {
        Aircraft aircraft = aircraftService.findById(id);
        if (aircraft == null) {
            log.error("getAircraftById: aircraft with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAircraftById: aircraft with id={} returned.", id);
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Добавить новую запись Aircraft")
    public ResponseEntity<?> saveAircraft(@RequestBody @Valid Aircraft aircraft) {
        log.info("saveAircraft: new aircraft saved.");
        return new ResponseEntity<>(aircraftService.save(aircraft), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Изменить Aircraft по \"Id\"")
    public ResponseEntity<Aircraft> editAircraft(@PathVariable("id") Long id,
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
    @ApiOperation(value = "Удалить Aircraft по \"Id\"")
    public ResponseEntity<String> deleteAircraft(@PathVariable("id") Long id) {
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
