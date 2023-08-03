package app.mappers;

import app.dto.BookingDTO;
import app.entities.Booking;

import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightService;
import app.services.interfaces.PassengerService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "passengerId", expression = "java((passengerService.findById(booking.getPassenger().getId())" +
                                                  ".get()).getId())")
    @Mapping(target = "flightId", expression = "java(flightService.findById(booking.getFlight().getId()).get().getId())")
    @Mapping(target = "categoryType", expression = "java(categoryService.findByCategoryType(booking.getCategory()" +
                                                   ".getCategoryType()).getCategoryType())")
    BookingDTO convertToBookingDTOEntity(Booking booking, @Context PassengerService passengerService,
                                         @Context FlightService flightService, @Context CategoryService categoryService);

    @Mapping(target = "passenger", expression = "java(passengerService.findById(bookingDTO.getPassengerId()).get())")
    @Mapping(target = "flight", expression = "java(flightService.findById(bookingDTO.getFlightId()).get())")
    @Mapping(target = "category", expression = "java(categoryService.findByCategoryType(bookingDTO.getCategoryType()))")
    Booking convertToBookingEntity(BookingDTO bookingDTO, @Context PassengerService passengerService,
                                   @Context FlightService flightService, @Context CategoryService categoryService);

}
