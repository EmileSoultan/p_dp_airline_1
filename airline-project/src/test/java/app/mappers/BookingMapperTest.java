package app.mappers;

import app.dto.BookingDTO;
import app.entities.Booking;
import app.enums.CategoryType;
import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Context;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

//class BookingMapperTest {

//    BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
//
//    private PassengerService passengerService;
//    private FlightService flightService;
//    private CategoryService categoryService;
//
//    @Test
//    public void shouldConvertBookingToBookingDTOEntity() {
//
//        Booking booking = new Booking();
//        booking.setId(1L);
//        booking.setBookingNumber("BK-111111");
//        booking.setBookingData(LocalDateTime.now());
//        booking.setPassenger(passengerService.findById(1001L).get().getId());
//        booking.setFlight(flightService.findById(4001L).get().getId());
//        booking.setCategory(CategoryType.ECONOMY);
//
//        BookingDTO bookingDTO = bookingMapper.convertToBookingDTOEntity(booking);
//
//        Assertions.assertEquals(booking.getId(), bookingDTO.getId());
//        Assertions.assertEquals(booking.getBookingNumber(), bookingDTO.getBookingNumber());
//        Assertions.assertEquals(booking.getBookingData(), bookingDTO.getBookingData());
//        Assertions.assertEquals(booking.getPassenger(), bookingDTO.getPassengerId());
//        Assertions.assertEquals(booking.getFlight(), bookingDTO.getFlightId());
//        Assertions.assertEquals(booking.getCategory(), bookingDTO.getCategoryType());
//
//    }
//
//    @Test
//    public void shouldConvertBookingDTOToBookingEntity() {
//
//        BookingDTO bookingDTO = new BookingDTO();
//        bookingDTO.setId(1L);
//        bookingDTO.setBookingNumber("BK-111111");
//        bookingDTO.setBookingData(LocalDateTime.now());
//        bookingDTO.setPassengerId(passengerService.findById(1001L).get().getId());
//        bookingDTO.setFlightId(flightService.findById(4001L).get().getId());
//        bookingDTO.setCategoryType(CategoryType.ECONOMY);
//
//        Booking booking = bookingMapper.convertToBookingEntity(bookingDTO);
//
//        Assertions.assertEquals(bookingDTO.getId(), booking.getId());
//        Assertions.assertEquals(bookingDTO.getBookingNumber(), booking.getBookingNumber());
//        Assertions.assertEquals(bookingDTO.getBookingData(), booking.getBookingData());
//        Assertions.assertEquals(bookingDTO.getPassengerId(), booking.getPassengerId());
//        Assertions.assertEquals(bookingDTO.getFlightId(), booking.getFlightId());
//        Assertions.assertEquals(bookingDTO.getCategoryType(), booking.getCategoryType());
//
//    }
//}
