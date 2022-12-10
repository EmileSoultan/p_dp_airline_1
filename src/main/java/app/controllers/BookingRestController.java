package app.controllers;

import app.entities.Booking;
import app.services.interfaces.BookingService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@Api("Booking API")
@Slf4j
public class BookingRestController {

    private final BookingService bookingService;

    @Autowired
    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ApiOperation(value = "Add new booking")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booking added successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid Booking booking) {
        log.info("createBooking: creating a new booking");
        return new ResponseEntity<>(bookingService.save(booking), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get list of all bookings")
    public ResponseEntity<List<Booking>> getListOfAllBookings() {
        log.info("getListOfAllBookings: search all bookings");
        List<Booking> bookings = bookingService.findAll();
        if (bookings == null) {
            log.info("getListOfAllBookings: list of bookings is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get booking by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "booking found"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id) {
        log.info("getBookingById: search booking by id = {}", id);
        Booking booking = bookingService.findById(id);
        if (booking == null) {
            log.info("getBookingById: not found booking with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/number")
    @ApiOperation(value = "Get booking by number")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "booking found"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<Booking> getBookingByNumber(
            @ApiParam(value = "Booking number", example = "SV-221122", required = true)
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
    @ApiOperation(value = "Delete booking by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "booking has been deleted"),
            @ApiResponse(code = 404, message = "booking not found")
    })
    public ResponseEntity<HttpStatus> deleteBookingById(@PathVariable("id") long id) {
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
    @ApiOperation(value = "Edit booking by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "booking has been edited"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "not found")
    })
    public ResponseEntity<Booking> editBookingById(@PathVariable("id") long id,
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
