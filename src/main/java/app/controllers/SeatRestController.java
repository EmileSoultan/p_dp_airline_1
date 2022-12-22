package app.controllers;

import app.entities.Seat;
import app.services.interfaces.SeatService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Seat REST")
@Tag(name = "Seat REST", description = "API для операций с местом в самолете")
@RestController
@RequestMapping("/api/seats")
@Slf4j
public class SeatRestController {
    private final SeatService seatService;

    @Autowired
    public SeatRestController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    @ApiOperation(value = "Create new Seat")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "seat create"),
            @ApiResponse(code = 400, message = "seat not create")
    })
    public ResponseEntity<HttpStatus> saveSeat(
            @ApiParam(
                    name = "seat",
                    value = "Seat model"
            )
            @RequestBody @Valid Seat seat) {
        seatService.save(seat);
        log.info("saveSeat: new seat saved with id= {}", seat.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Seat by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "seat found"),
            @ApiResponse(code = 404, message = "seat not found")
    })
    public ResponseEntity<Seat> getSeatById(
            @ApiParam(
                    name = "id",
                    value = "Seat.id"
            )
            @PathVariable("id") long id) {

        Seat seat = seatService.findById(id);

        if (seat != null) {
            log.info("getSeatById: find seat with id = {}", id);
            return new ResponseEntity<>(seat, HttpStatus.OK);
        } else {
            log.info("getSeatById: not find seat with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit Seat by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "seat edited"),
            @ApiResponse(code = 400, message = "seat failed to edit")
    })
    public ResponseEntity<HttpStatus> editSeat(
            @ApiParam(
                    name = "id",
                    value = "Seat.id"
            )
            @PathVariable("id") long id,
            @ApiParam(
                    name = "seat",
                    value = "Seat model"
            )
            @RequestBody @Valid Seat seat) {
        if (seatService.findById(id) == null) {
            log.error("editSeat: error of editing seat by id = {} - not find seat.", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        seatService.save(seat);
        log.info("editSeat: the seat with id = {} has been edited.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
