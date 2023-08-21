package app.mappers;

import app.dto.BookingDTO;
import app.entities.Booking;
import app.entities.Category;
import app.entities.Flight;
import app.entities.Passenger;
import app.enums.CategoryType;
import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightSeatService;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;


class BookingMapperTest {

    BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    @Mock
    private PassengerService passengerServiceMock = Mockito.mock(PassengerService.class);
    @Mock
    private FlightService flightServiceMock = Mockito.mock(FlightService.class);

    @Mock
    private FlightSeatService flightSeatServiceMock = Mockito.mock(FlightSeatService.class);

    @Mock
    private CategoryService categoryServiceMock = Mockito.mock(CategoryService.class);

    @Test
    public void shouldConvertBookingToBookingDTOEntity() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setId(1001L);
        when(passengerServiceMock.getPassengerById(1001L)).thenReturn(Optional.of(passenger));


        Flight flight = new Flight();
        flight.setId(4001L);
        when(flightServiceMock.getFlightById(4001L)).thenReturn(Optional.of(flight));

        Category category = new Category();
        category.setCategoryType(CategoryType.ECONOMY);
        when(categoryServiceMock.getCategoryByType(CategoryType.ECONOMY)).thenReturn(category);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBookingNumber("BK-111111");
        booking.setBookingDate(LocalDateTime.now());
        booking.setPassenger(passengerServiceMock.getPassengerById(1001L).get());
       // booking.setFlight(flightServiceMock.getFlightById(4001L).get());
        booking.setFlightSeat(flightSeatServiceMock.getFlightSeatById(4001L).get());
        booking.setCategory(categoryServiceMock.getCategoryByType(CategoryType.ECONOMY));

        BookingDTO bookingDTO = bookingMapper.convertToBookingDTOEntity(booking, passengerServiceMock, flightServiceMock,
                categoryServiceMock);

        Assertions.assertEquals(booking.getId(), bookingDTO.getId());
        Assertions.assertEquals(booking.getBookingNumber(), bookingDTO.getBookingNumber());
        Assertions.assertEquals(booking.getBookingDate(), bookingDTO.getBookingDate());
        Assertions.assertEquals(booking.getPassenger().getId(), bookingDTO.getPassengerId());
        Assertions.assertEquals(booking.getFlightSeat().getId(), bookingDTO.getFlightSeatId());
        Assertions.assertEquals(booking.getCategory().getCategoryType(), bookingDTO.getCategoryType());

    }

    @Test
    public void shouldConvertBookingDTOToBookingEntity() throws Exception {

        Passenger passenger = new Passenger();
        passenger.setId(1001L);
        when(passengerServiceMock.getPassengerById(1001L)).thenReturn(Optional.of(passenger));

        Flight flight = new Flight();
        flight.setId(4001L);
        when(flightServiceMock.getFlightById(4001L)).thenReturn(Optional.of(flight));

        Category category = new Category();
        category.setCategoryType(CategoryType.ECONOMY);
        when(categoryServiceMock.getCategoryByType(CategoryType.ECONOMY)).thenReturn(category);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        bookingDTO.setBookingNumber("BK-111111");
        bookingDTO.setBookingDate(LocalDateTime.now());
        bookingDTO.setPassengerId(passengerServiceMock.getPassengerById(1001L).get().getId());
        bookingDTO.setFlightSeatId(flightServiceMock.getFlightById(4001L).get().getId());
        bookingDTO.setCategoryType(CategoryType.ECONOMY);

        Booking booking = bookingMapper.convertToBookingEntity(bookingDTO, passengerServiceMock, flightServiceMock, categoryServiceMock);

        Assertions.assertEquals(bookingDTO.getId(), booking.getId());
        Assertions.assertEquals(bookingDTO.getBookingNumber(), booking.getBookingNumber());
        Assertions.assertEquals(bookingDTO.getBookingDate(), booking.getBookingDate());
        Assertions.assertEquals(bookingDTO.getPassengerId(), booking.getPassenger().getId());
        Assertions.assertEquals(bookingDTO.getFlightSeatId(), booking.getFlightSeat().getId());
        Assertions.assertEquals(bookingDTO.getCategoryType(), booking.getCategory().getCategoryType());

    }
}
