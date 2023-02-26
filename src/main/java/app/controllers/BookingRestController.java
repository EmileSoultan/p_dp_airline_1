package app.controllers;

import app.entities.Booking;
import app.services.interfaces.BookingService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Booking REST")
@Tag(name = "Booking REST", description = "API для операций с бронированием")
@RestController
@RequestMapping("/api/booking")
@Slf4j
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    @PostMapping
    @ApiOperation(value = "Create new Booking")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booking added successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<Booking> createBooking(
            @ApiParam(
                    name = "booking",
                    value = "Booking model"
            )
            @RequestBody @Valid Booking booking) {
        log.info("createBooking: creating a new booking");
        return new ResponseEntity<>(bookingService.save(booking), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get list of all Booking")
    public ResponseEntity<List<Booking>> getListOfAllBookings(Pageable pageable) {
        log.info("getListOfAllBookings: search all bookings");
        Page<Booking> bookings = bookingService.findAll(pageable);
        if (bookings == null) {
            log.info("getListOfAllBookings: list of bookings is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookings.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Booking by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "booking found"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<Booking> getBookingById(
            @ApiParam(
                    name = "id",
                    value = "Booking.id"
            )
            @PathVariable("id") long id) {
        log.info("getBookingById: search booking by id = {}", id);
        Booking booking = bookingService.findById(id);
        if (booking == null) {
            log.info("getBookingById: not found booking with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/number")
    @ApiOperation(value = "Get Booking by bookingNumber")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "booking found"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<Booking> getBookingByNumber(
            @ApiParam(
                    value = "bookingNumber",
                    example = "SV-221122",
                    required = true)
            @RequestParam(value = "bookingNumber") String bookingNumber) {
        log.info("getBookingByNumber: search booking by number = {}", bookingNumber);
        Booking booking = bookingService.findByBookingNumber(bookingNumber);
        if (booking == null) {
            log.info("getBookingByNumber: not found booking with number = {}", bookingNumber);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Booking by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "booking has been deleted"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<HttpStatus> deleteBookingById(
            @ApiParam(
                    name = "id",
                    value = "Booking.id"
            )
            @PathVariable("id") long id) {
        log.info("deleteBookingById: deleting a booking with id = {}", id);
        try {
            bookingService.deleteById(id);
        } catch (Exception e) {
            log.error("deleteBookingById: error of deleting - booking with id = {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit Booking by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "booking has been edited"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "not found")
    })
    public ResponseEntity<Booking> editBookingById(
            @ApiParam(
                    name = "id",
                    value = "Booking.id"
            )
            @PathVariable("id") long id,
            @ApiParam(
                    name = "booking",
                    value = "Booking model"
            )
            @RequestBody @Valid Booking booking) {
        log.info("editBookingById: edit booking with id = {}", id);
        if (bookingService.findById(id) == null) {
            log.info("editBookingById: not found booking with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        booking.setId(id);
        return new ResponseEntity<>(bookingService.save(booking), HttpStatus.OK);
    }
}
