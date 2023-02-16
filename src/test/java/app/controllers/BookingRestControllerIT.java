package app.controllers;

import app.entities.Booking;
import app.enums.CategoryType;
import app.services.interfaces.BookingService;
import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql({"/sqlQuery/delete-from-tables.sql"})
@Sql({"/sqlQuery/create-passengerAircraftDestinationFlightsCategoryBooking-before.sql"})
@Transactional
class BookingRestControllerIT extends IntegrationTestBase {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FlightService flightService;


    @Test
    @DisplayName("Save Booking")
    void shouldSaveBooking() throws Exception {
        Booking booking = new Booking();
        booking.setBookingNumber("BK-111111");
        booking.setBookingData(LocalDateTime.now());
        booking.setPassenger(passengerService.findById(1001L));
        booking.setFlight(flightService.getById(4001L));
        booking.setCategory(categoryService.findByCategoryType(CategoryType.ECONOMY));

        mockMvc.perform(post("http://localhost:8080/api/booking")
                        .content(objectMapper.writeValueAsString(booking))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Get All Bookings")
    void shouldGetAllBookings() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/booking"))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(content().json(objectMapper.writeValueAsString(bookingService.findAll(pageable))));
    }


    @Test
    @DisplayName("Get Booking by ID")
    void shouldGetBookingById() throws Exception {
        long id = 6001;
        mockMvc.perform(get("http://localhost:8080/api/booking/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingService.findById(id))));
    }


    @Test
    @DisplayName("Get Booking by Number")
    void shouldGetBookingByNumber() throws Exception {
        String bookingNumber = "000000001";
        mockMvc.perform(get("http://localhost:8080/api/booking/number")
                        .param("bookingNumber", bookingNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingService.findByBookingNumber(bookingNumber))));
    }


    @Test
    @DisplayName("Edit Booking by ID")
    void shouldEditBookingById() throws Exception {
        long id = 6002;
        Booking booking = bookingService.findById(id);
        booking.setBookingNumber("BK-222222");
        booking.setBookingData(LocalDateTime.now());
        booking.setPassenger(passengerService.findById(1002L));
        booking.setFlight(flightService.getById(4002L));
        booking.setCategory(categoryService.findByCategoryType(CategoryType.BUSINESS));

        mockMvc.perform(patch("http://localhost:8080/api/booking/{id}", id)
                        .content(
                                objectMapper.writeValueAsString(booking)
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(booking)));
    }


    @Test
    @DisplayName("Delete Booking by ID")
    void shouldDeleteById() throws Exception {
        long id = 6003;
        mockMvc.perform(delete("http://localhost:8080/api/booking/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        mockMvc.perform(get("http://localhost:8080/api/booking/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}