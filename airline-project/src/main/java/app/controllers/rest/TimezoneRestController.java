package app.controllers.rest;

import app.controllers.api.rest.TimezoneRestApi;
import app.dto.TimezoneDTO;
import app.entities.Timezone;
import app.services.interfaces.TimezoneService;
import app.util.mappers.TimezoneMapper;
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
public class TimezoneRestController implements TimezoneRestApi {

    private final TimezoneService timezoneService;
    private final TimezoneMapper timezoneMapper;

    @Override
    public ResponseEntity<Page<TimezoneDTO>> getAll(Integer page, Integer size) {
        var timezone = timezoneService.findAll(page, size);
        if (timezone == null) {
            log.error("getAll: Timezones not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAll: Find all timezones");
        var timezoneDTOS = timezone.stream().map(TimezoneDTO:: new).collect(Collectors.toList());
        return new ResponseEntity<>(new PageImpl<>(timezoneDTOS, PageRequest.of(page, size), timezone.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TimezoneDTO> getById(Long id) {
        log.info("getById: search Timezone by id = {}", id);
        var timezone = timezoneService.getTimezoneById(id);

        if (timezone.isEmpty()) {
            log.info("getById: not found Timezone with id = {} doesn't exist", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TimezoneDTO(timezone.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TimezoneDTO> create(TimezoneDTO timezoneDTO) {
        log.info("create: new Timezone");
        return new ResponseEntity<>(new TimezoneDTO(timezoneService.saveTimezone(timezoneMapper.convertToTimezoneEntity(timezoneDTO))),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TimezoneDTO> update(Long id, TimezoneDTO timezoneDTO) {
        timezoneDTO.setId(id);
        log.info("update: timezone = {}", timezoneDTO);
        return new ResponseEntity<>(new TimezoneDTO(timezoneService.update(timezoneMapper.convertToTimezoneEntity(timezoneDTO))),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        log.info("delete: deleting a Timezone with id = {}", id);
        try {
            timezoneService.deleteById(id);
        } catch (Exception e) {
            log.error("delete: Timezone with id = {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}