package app.util.mappers;

import app.dto.BookingDTO;
import app.entities.Booking;
import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final FlightService flightService;
    private final CategoryService categoryService;
    private final PassengerService passengerService;

    public Booking convertToBookingEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setBookingNumber(bookingDTO.getBookingNumber());
        booking.setBookingData(bookingDTO.getBookingData());
        booking.setPassenger((passengerService.findById(bookingDTO.getPassengerId()).get()));
        booking.setFlight(flightService.getById(bookingDTO.getFlightId()));
        booking.setCategory(categoryService.findByCategoryType(bookingDTO.getCategoryType()));
        return booking;
    }

    public BookingDTO convertToBookingDTOEntity(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookingNumber(booking.getBookingNumber());
        bookingDTO.setBookingData(booking.getBookingData());
        bookingDTO.setPassengerId((passengerService.findById(booking.getPassenger().getId()).get()).getId());
        bookingDTO.setFlightId(flightService.getById(booking.getFlight().getId()).getId());
        bookingDTO.setCategoryType(categoryService.findByCategoryType(booking.getCategory().getCategoryType()).getCategoryType());

        return bookingDTO;
    }
}
