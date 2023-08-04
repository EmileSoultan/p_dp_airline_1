package app.controllers.rest;

import app.controllers.api.rest.AircraftRestApi;
import app.dto.AircraftDTO;
import app.entities.Aircraft;
import app.services.interfaces.AircraftService;
import app.util.mappers.AircraftMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AircraftRestController implements AircraftRestApi {

    private final AircraftService aircraftService;
    private final AircraftMapper aircraftMapper;

    @Override
    public ResponseEntity<Page<AircraftDTO>> getAll(Integer page, Integer size) {
        log.info("getAll: get all Aircrafts");
        Page<AircraftDTO> aircrafts = aircraftService.findAll(page, size).map(entity -> {
            return aircraftMapper.convertToAircarftDTOEntity(entity);
        });
        return aircrafts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(aircrafts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AircraftDTO> getById(Long id) {
        var aircraft = aircraftService.findById(id);
        if (aircraft == null) {
            log.error("getById: Aircraft with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getById: Aircraft with id={} returned.", id);
        return new ResponseEntity<>(new AircraftDTO(aircraft), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Aircraft> create(AircraftDTO aircraftDTO) {
        log.info("create: new Aircraft saved.");
        return new ResponseEntity<>(aircraftService.save(aircraftMapper
                .convertToAircraftEntity(aircraftDTO)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Aircraft> update(Long id, AircraftDTO aircraftDTO) {
        if (aircraftService.findById(id) == null) {
            log.error("update: Aircraft with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        aircraftDTO.setId(id);
        log.info("update: the Aircraft with id={} has been edited.", id);
        return ResponseEntity.ok(aircraftService.save(aircraftMapper
                .convertToAircraftEntity(aircraftDTO)));
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        try {
            aircraftService.delete(id);
            log.info("delete: the Aircraft with id={} has been deleted.", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("delete: error of deleting - Aircraft with id={} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}